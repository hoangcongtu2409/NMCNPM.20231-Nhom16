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
import teachingAidManagementSystem.classes.Devices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DevicesUIController implements Initializable {
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private AnchorPane addAndEditWindow;
    @FXML
    private HBox addButtons;
    @FXML
    private HBox editButtons;
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
    private Devices device;

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
        //TODO Sửa lại sau khi có database: devicesList sẽ lấy danh sách thiết bị từ database
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

    //Thêm các nút edit vào các hàng
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
                                TableRow<Devices> row = getTableRow();
                                if (row != null) {
                                    device = row.getItem();
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

    //Mở của sổ để thêm thiết bị
    @FXML
    public void openAddWindow() {
        addAndEditWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        addButtons.setVisible(true);
        editButtons.setVisible(false);
        iconTextField.setText(null);
        idTextField.setText(null);
        nameTextField.setText(null);
        amountTextField.setText(null);
        usableTextField.setText(null);
        brokenTextField.setText(null);
    }

    //Mở của sổ chỉnh sửa thiết bị
    @FXML
    public void openEditWindow() {
        addAndEditWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        addButtons.setVisible(false);
        editButtons.setVisible(true);
        iconTextField.setText(device.getIconDevice());
        idTextField.setText(device.getIdDevice());
        nameTextField.setText(device.getNameDevice());
        amountTextField.setText(String.valueOf(device.getAmountDevice()));
        usableTextField.setText(String.valueOf(device.getUsableDevice()));
        brokenTextField.setText(String.valueOf(device.getBrokenDevice()));
    }

    //Đóng Popup window
    @FXML
    public void closePopup() {
        addAndEditWindow.setVisible(false);
        mainWindow.setEffect(null);
    }

    //TODO Với các phần thêm, xóa, sửa thì phải cập nhật lên database
    public void addDevice (ActionEvent event) {
        Devices newDevice = new Devices();
        newDevice.setIconDevice(iconTextField.getText());
        newDevice.setIdDevice(idTextField.getText());
        newDevice.setNameDevice(nameTextField.getText());
        newDevice.setAmountDevice(Integer.parseInt(amountTextField.getText()));
        newDevice.setUsableDevice(Integer.parseInt(usableTextField.getText()));
        newDevice.setBrokenDevice(Integer.parseInt(brokenTextField.getText()));
        devicesList.add(newDevice);
        closePopup();
    }

    public void deleteDevice(ActionEvent event) {
        devicesList.remove(device);
        closePopup();
    }

    //Lưu lại thay đổi thiết bị
    public void applyChanges (ActionEvent event) {
        Devices updateDevice = new Devices();
        updateDevice.setIconDevice(iconTextField.getText());
        updateDevice.setIdDevice(idTextField.getText());
        updateDevice.setNameDevice(nameTextField.getText());
        updateDevice.setAmountDevice(Integer.parseInt(amountTextField.getText()));
        updateDevice.setUsableDevice(Integer.parseInt(usableTextField.getText()));
        updateDevice.setBrokenDevice(Integer.parseInt(brokenTextField.getText()));
        int i = 0;
        if (devicesList.get(i).equals(device))
            devicesList.set(i, updateDevice);
        closePopup();
    }
}
