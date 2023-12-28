package teachingAidManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
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
    private TableColumn<Devices, Void> editColumn;
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
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Devices, Void>, TableCell<Devices, Void>> cellFactory = new Callback<TableColumn<Devices, Void>, TableCell<Devices, Void>>() {
            @Override
            public TableCell<Devices, Void> call(final TableColumn<Devices, Void> devicesVoidTableColumn) {
                final TableCell<Devices, Void> cell= new TableCell<Devices, Void>() {
                    final Button btn = new Button("Edit");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //Chuyển sang giao diện giống add nhưng có sẵn thông tin của devices cần sửa
                            btn.setOnAction(e -> {

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
    //Cái này nên làm chuyển sang giao diện khác hoặc popup window để add vào
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
