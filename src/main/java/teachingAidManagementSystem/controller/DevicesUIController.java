package teachingAidManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.classes.Devices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DevicesUIController implements Initializable {
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

    //Điều khiển TableView
    @FXML
    private TableView<Devices> table;
    @FXML
    private TableColumn<Devices, String> iconColumn;
    @FXML
    private TableColumn<Devices, String> idColumn;
    @FXML
    private TableColumn<Devices, String> nameColumn;
    @FXML
    private TableColumn<Devices, Integer> amountColumn;
    @FXML
    private TableColumn<Devices, Integer> usableColumn;
    @FXML
    private TableColumn<Devices, Integer> brokenColumn;

    @FXML
    private TextField iconTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField usableTextField;
    @FXML
    private TextField brokenTextField;

    private ObservableList<Devices> devicesList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        devicesList = FXCollections.observableArrayList(
                new Devices("MIC", "MIC", "Micro", 22, 12, 3),
                new Devices("Mic", "micro", "míc", 22, 22, 22)
        );
        iconColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>("iconDevice"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>( "idDevice"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>( "nameDevice"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>( "amountDevice"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>( "usableDevice"));
        brokenColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>( "brokenDevice"));
        table.setItems(devicesList);
    }

    public void addButton (ActionEvent event) {
        Devices newDevice = new Devices();
        newDevice.setIconDevice(iconTextField.getText());
        newDevice.setIdDevice(idTextField.getText());
        newDevice.setNameDevice(nameTextField.getText());
        newDevice.setAmountDevice(Integer.parseInt(amountTextField.getText()));
        newDevice.setUsableDevice(Integer.parseInt(usableTextField.getText()));
        newDevice.setBrokenDevice(Integer.parseInt(brokenTextField.getText()));
        devicesList.add(newDevice);
    }

    public void deleteButton (ActionEvent event) {
        Devices selected = table.getSelectionModel().getSelectedItem();
        devicesList.remove(selected);
    }

    public void selectDevice (ActionEvent event) {
        Devices selected = table.getSelectionModel().getSelectedItem();
        iconTextField.setText(selected.getIconDevice());
        idTextField.setText(selected.getIdDevice());
        nameTextField.setText(selected.getNameDevice());
        amountTextField.setText(String.valueOf(selected.getAmountDevice()));
        usableTextField.setText(String.valueOf(selected.getUsableDevice()));
        brokenTextField.setText(String.valueOf(selected.getBrokenDevice()));
    }

}
