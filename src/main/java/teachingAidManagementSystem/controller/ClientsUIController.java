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
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.DatabaseConnection;
import teachingAidManagementSystem.classes.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static teachingAidManagementSystem.controller.LoginUIController.admin;

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
    private FlowPane allClientPane;
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

    @FXML
    public void logout() throws IOException {
        admin = null;
        App.setRoot("loginUI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientList = FXCollections.observableArrayList();
        getDataList();

        for(Client x : clientList) {
            AnchorPane pane = createPane(x);
            allClientPane.getChildren().add(pane);
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

        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setGlyphName("TIMES");
        icon.setSize("20");
        icon.setFill(Paint.valueOf("#767676"));
        icon.setOnMouseClicked(e -> {
            this.client = client;
            deleteClient();
        });

        pane.getChildren().addAll(nameLabel, departmentLabel, btn, icon);
        AnchorPane.setTopAnchor(nameLabel, 145.0);
        AnchorPane.setLeftAnchor(nameLabel, 27.0);
        AnchorPane.setTopAnchor(departmentLabel, 184.0);
        AnchorPane.setLeftAnchor(departmentLabel, 27.0);
        AnchorPane.setTopAnchor(btn, 225.0);
        AnchorPane.setLeftAnchor(btn, 60.8);
        AnchorPane.setTopAnchor(icon, 15.0);
        AnchorPane.setRightAnchor(icon, 15.0);
        pane.setStyle("-fx-background-color: #3B3B3B;-fx-background-radius: 20; -fx-border-radius: 20;");

        return pane;
    }

    @FXML
    private void openAddWindow() {
        mainWindow.setDisable(true);
        addWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        nameAddTextField.setText(null);
        emailAddTextField.setText(null);
        phoneAddTextField.setText(null);
        departmentAddTextField.setText(null);
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
    private void addClient() {
        Client newClient = new Client();
        newClient.setName(nameAddTextField.getText());
        newClient.setEmail(emailAddTextField.getText());
        newClient.setPhoneNumber(phoneAddTextField.getText());
        newClient.setDepartment(departmentAddTextField.getText());
        newClient.addClient();

        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String getClientID = "SELECT TOP 1 ClientID\n" +
                "FROM Client\n" +
                "ORDER BY ClientID DESC";

        try {
            PreparedStatement statement = connectDB.prepareStatement(getClientID);
            ResultSet rs = statement.executeQuery();

            rs.next();
            newClient.setClientID(rs.getInt("ClientID"));

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        clientList.add(newClient);

        AnchorPane pane = createPane(newClient);
        allClientPane.getChildren().add(pane);
        closePopup();
    }

    @FXML
    private void saveChanges() {
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
                allClientPane.getChildren().set(i, pane);

                break;
            }
        }
        closePopup();
    }

    public void deleteClient() {
        client.deleteClient();
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).equals(client)) {
                clientList.remove(i);
                allClientPane.getChildren().remove(i);
                break;
            }
        }
    }
}
