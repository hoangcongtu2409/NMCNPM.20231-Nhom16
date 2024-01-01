package teachingAidManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.classes.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsUIController implements Initializable {
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private AnchorPane addAndEditWindow;
    @FXML
    private Button switchButton;
    @FXML
    private HBox addButtons;
    @FXML
    private HBox editButtons;
    @FXML
    private TableView<Client> allClientsTable;
    @FXML
    private TableView<Client> usingClientsTable;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;
    @FXML
    private TableColumn<Client, String> phoneNumberColumn;
    @FXML
    private TableColumn<Client, String> addressColumn;
    @FXML
    private TableColumn<Client, Void> editColumn;
    @FXML
    private TableColumn<Client, String> descriptionColumn;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField addressTextField;
    private ObservableList<Client> ClientsList;
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
        ClientsList = FXCollections.observableArrayList(
                new Client("Nguyễn Việt Hoàng", "hoang@gmail.com", "0123456789", "Thái Bình")
        );
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        allClientsTable.setItems(ClientsList);
        usingClientsTable.setItems(ClientsList);
        addButtonToTable();
    }
    //Thêm các nút detail vào các hàng
    private void addButtonToTable() {
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> ClientsVoidTableColumn) {
                final TableCell<Client, Void> cell= new TableCell<Client, Void>() {
                    final Button btn = new Button("Detail");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //Chuyển sang giao diện giống add nhưng có sẵn thông tin của Clients cần sửa
                            btn.setOnAction(e -> {
                                TableRow<Client> row = getTableRow();
                                if (row != null) {
                                    client = row.getItem();
                                    openEditWindow();
                                }
                            });
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        editColumn.setCellFactory(cellFactory);
    }
    //Mở của sổ để thêm giảng viên
    @FXML
    public void openAddWindow() {
        addAndEditWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        addButtons.setVisible(true);
        editButtons.setVisible(false);
        nameTextField.setText(null);
        emailTextField.setText(null);
        phoneNumberTextField.setText(null);
        addressTextField.setText(null);
    }
    //Mở của sổ chỉnh sửa giảng viên
    @FXML
    public void openEditWindow() {
        addAndEditWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        addButtons.setVisible(false);
        editButtons.setVisible(true);
        nameTextField.setText(client.getName());
        emailTextField.setText(client.getEmail());
        phoneNumberTextField.setText(client.getPhoneNumber());
        addressTextField.setText(client.getAddress());
    }
    //Đóng Popup window
    @FXML
    public void closePopup() {
        addAndEditWindow.setVisible(false);
        mainWindow.setEffect(null);
    }
    public void addClient(ActionEvent event) {
        Client newClient = new Client();
        newClient.setName(nameTextField.getText());
        newClient.setEmail(emailTextField.getText());
        newClient.setPhoneNumber(phoneNumberTextField.getText());
        newClient.setAddress(addressTextField.getText());
        ClientsList.add(newClient);
        closePopup();
    }
    public void deleteClient(ActionEvent event) {
        ClientsList.remove(client);
        closePopup();
    }
    public void applyChanges(ActionEvent event) {
        Client updateClient = new Client();
        updateClient.setName(nameTextField.getText());
        updateClient.setEmail(emailTextField.getText());
        updateClient.setPhoneNumber(phoneNumberTextField.getText());
        updateClient.setAddress(addressTextField.getText());
        int i = 0;
        if (ClientsList.get(i).equals(client))
            ClientsList.set(i, updateClient);
        closePopup();
    }
    @FXML
    public void switchTable(ActionEvent event) {
        if (allClientsTable.isVisible()) {
            switchButton.setText("All Clients");
            allClientsTable.setVisible(false);
            usingClientsTable.setVisible(true);
        } else {
            switchButton.setText("Using Clients");
            allClientsTable.setVisible(true);
            usingClientsTable.setVisible(false);
        }
    }
}
