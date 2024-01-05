package teachingAidManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.DatabaseConnection;
import teachingAidManagementSystem.classes.Client;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsUIController implements Initializable {
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private AnchorPane addWindow;
    @FXML
    private TextField nameAddTextField;
    @FXML
    private TextField emailAddTextField;
    @FXML
    private TextField phoneAddTextField;
    @FXML
    private TextField departmentAddTextField;
    @FXML
    private AnchorPane editWindow;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField departmentTextField;
    @FXML
    private VBox allClientsBox;
    private List<HBox> tripleFrameList = new ArrayList<>();
    private List<AnchorPane> frameList = new ArrayList<>();
    private ObservableList<Client> clientList;
    private Client client;

    @FXML
    public void switchToHome() throws IOException {
        App.setRoot("homeUI");
    }
    @FXML
    public void switchToManage() throws IOException {
        App.setRoot("manageUI");
    }
    @FXML
    public void switchToDevices() throws IOException {
        App.setRoot("devicesUI");
    }
    @FXML
    public void switchToClients() throws IOException {
        App.setRoot("clientsUI");
    }
    @FXML
    public void switchToProfile() throws IOException {
        App.setRoot("profileUI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientList = FXCollections.observableArrayList();
        getDataList();

        for(Client x : clientList) {
            addFrame(x);
        }
    }

    private void getDataList() {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String selectAllData ="SELECT * FROM Client";
        try {
            PreparedStatement statement = connectDB.prepareStatement(selectAllData);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Client newClient = new Client();
                newClient.setClientID(rs.getInt("ClientID"));
                newClient.setName(rs.getNString("Name"));
                newClient.setEmail(rs.getString("Email"));
                newClient.setPhoneNumber(rs.getString("PhoneNumber"));
                newClient.setDepartment(rs.getNString("Department"));
                clientList.add(newClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void addFrame(Client client) {
        int i = frameList.size();
        if (i % 3 == 0) {
            HBox hBox = new HBox();
            hBox.setPrefHeight(270.4);
            hBox.setSpacing(46);
            allClientsBox.getChildren().add(hBox);
            tripleFrameList.add(hBox);
        }

        AnchorPane pane = createPane(client);

        int j = tripleFrameList.size() - 1;
        tripleFrameList.get(j).getChildren().add(pane);
        frameList.add(pane);
    }

    private AnchorPane createPane(Client client) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(316);
        pane.setPrefHeight(270.4);

        Label nameLabel = new Label(client.getName());
        nameLabel.setPrefSize(224, 27.2);
        nameLabel.setFont(Font.font(24));
        nameLabel.setTextFill(Paint.valueOf("white"));
        nameLabel.setPadding(new Insets(-10, 0, -10, 0));

        Label departmentLabel = new Label(client.getDepartment());
        departmentLabel.setPrefSize(161.6, 21.6);
        departmentLabel.setFont(Font.font(20));
        departmentLabel.setTextFill(Paint.valueOf("white"));
        departmentLabel.setPadding(new Insets(-10, 0, -10, 0));

        Button btn = new Button("Edit Client Profile");
        btn.setPrefSize(194.4, 30.4);
        btn.setFont(Font.font(14));
        btn.setTextFill(Paint.valueOf("white"));
        btn.setStyle("-fx-background-color: #767676;-fx-background-radius: 10; -fx-border-radius: 10;");
        btn.setOnAction(e -> {
            this.client = client;
            openEditWindow();
        });

        pane.getChildren().addAll(nameLabel, departmentLabel, btn);
        AnchorPane.setTopAnchor(nameLabel, 145.0);
        AnchorPane.setLeftAnchor(nameLabel, 27.0);
        AnchorPane.setTopAnchor(departmentLabel, 184.0);
        AnchorPane.setLeftAnchor(departmentLabel, 27.0);
        AnchorPane.setTopAnchor(btn, 225.0);
        AnchorPane.setLeftAnchor(btn, 60.8);
        pane.setStyle("-fx-background-color: #3B3B3B;-fx-background-radius: 20; -fx-border-radius: 20;");

        return pane;
    }

    @FXML
    private void openAddWindow() {
        mainWindow.setDisable(true);
        addWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
    }

    @FXML
    private void openEditWindow() {
        mainWindow.setDisable(true);
        editWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        nameTextField.setText(client.getName());
        emailTextField.setText(client.getEmail());
        phoneTextField.setText(client.getPhoneNumber());
        departmentTextField.setText(client.getDepartment());
    }

    @FXML
    private void closePopup() {
        addWindow.setVisible(false);
        editWindow.setVisible(false);
        mainWindow.setDisable(false);
        mainWindow.setEffect(null);
    }

    @FXML
    private void addClient() throws SQLException {
        Client newClient = new Client();
        newClient.setName(nameAddTextField.getText());
        newClient.setEmail(emailAddTextField.getText());
        newClient.setPhoneNumber(phoneAddTextField.getText());
        newClient.setDepartment(departmentAddTextField.getText());
        clientList.add(newClient);
        newClient.addClient();
        addFrame(newClient);
        closePopup();
    }

    @FXML
    private void saveChanges() throws SQLException {
        Client updateClient = new Client();
        updateClient.setClientID(client.getClientID());
        updateClient.setName(nameTextField.getText());
        updateClient.setEmail(emailTextField.getText());
        updateClient.setPhoneNumber(phoneTextField.getText());
        updateClient.setDepartment(departmentTextField.getText());

        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).equals(client)) {
                clientList.set(i, updateClient);
                updateClient.updateClient();

                AnchorPane pane = createPane(updateClient);
                tripleFrameList.get(i / 3).getChildren().set(i % 3, pane);
                frameList.set(i, pane);

                break;
            }
        }
        closePopup();
    }
}
