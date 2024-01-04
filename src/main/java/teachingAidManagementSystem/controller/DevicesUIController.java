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
import teachingAidManagementSystem.DatabaseConnection;
import teachingAidManagementSystem.classes.Device;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DevicesUIController implements Initializable {
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private Button switchButton;
    @FXML
    private TableView<Device> allDevicesTable;
    @FXML
    private TableColumn<Device, String> iconColumn;
    @FXML
    private TableColumn<Device, String> idColumn;
    @FXML
    private TableColumn<Device, String> nameColumn;
    @FXML
    private TableColumn<Device, Integer> amountColumn;
    @FXML
    private TableColumn<Device, Integer> usableColumn;
    @FXML
    private TableColumn<Device, Integer> brokenColumn;
    @FXML
    private TableColumn<Device, Void> editColumn;
    @FXML
    private TableView<Device> brokenDevicesTable;
    @FXML
    private TableColumn<Device, String> idBrokenColumn;
    @FXML
    private TableColumn<Device, String> nameBrokenColumn;
    @FXML
    private TableColumn<Device, Integer> amountBrokenColumn;
    @FXML
    private TableColumn<Device, String> descriptionBrokenColumn;
    @FXML
    private AnchorPane addWindow;
    @FXML
    private TextField idAddTextField;
    @FXML
    private TextField nameAddTextField;
    @FXML
    private TextField amountAddTextField;
    @FXML
    private TextField descriptionAddTextField;
    @FXML
    private AnchorPane editWindow;
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
    private ObservableList<Device> deviceList;
    private ObservableList<Device> brokenList;
    private Device device;

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
        deviceList = FXCollections.observableArrayList();
        brokenList = FXCollections.observableArrayList();
        getDataList();

        iconColumn.setCellValueFactory(new PropertyValueFactory<Device, String>("icon"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Device, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Device, String>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Device, Integer>("amount"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<Device, Integer>("usable"));
        brokenColumn.setCellValueFactory(new PropertyValueFactory<Device, Integer>("broken"));
        allDevicesTable.setItems(deviceList);

        idBrokenColumn.setCellValueFactory(new PropertyValueFactory<Device, String>("id"));
        nameBrokenColumn.setCellValueFactory(new PropertyValueFactory<Device, String>("name"));
        amountBrokenColumn.setCellValueFactory(new PropertyValueFactory<Device, Integer>("broken"));
        descriptionBrokenColumn.setCellValueFactory(new PropertyValueFactory<Device, String>("description"));
        brokenDevicesTable.setItems(brokenList);

        addButtonToTable();
    }

    //Lấy dữ liệu từ database
    private void getDataList() {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String selectAllData ="SELECT * FROM Device";
        try {
            PreparedStatement statement = connectDB.prepareStatement(selectAllData);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Device newDevice = new Device();
                newDevice.setId(rs.getString("ID"));
                newDevice.setName(rs.getNString("Name"));
                newDevice.setAmount(rs.getInt("Amount"));
                newDevice.setUsable(rs.getInt("Usable"));
                newDevice.setBroken(rs.getInt("Broken"));
                newDevice.setDescription(rs.getNString("Description"));
                deviceList.add(newDevice);
                if (newDevice.getBroken() != 0)
                    brokenList.add(newDevice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Thêm các nút edit vào các hàng
    private void addButtonToTable() {
        Callback<TableColumn<Device, Void>, TableCell<Device, Void>> cellFactory = new Callback<TableColumn<Device, Void>, TableCell<Device, Void>>() {
            @Override
            public TableCell<Device, Void> call(final TableColumn<Device, Void> devicesVoidTableColumn) {
                final TableCell<Device, Void> cell= new TableCell<Device, Void>() {
                    final Button btn = new Button("Edit");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //Chuyển sang giao diện giống add nhưng có sẵn thông tin của devices cần sửa
                            btn.setOnAction(e -> {
                                TableRow<Device> row = getTableRow();
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
        mainWindow.setDisable(true);
        addWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        idTextField.setText(null);
        nameTextField.setText(null);
        amountTextField.setText(null);
        usableTextField.setText(null);
        brokenTextField.setText(null);
    }

    //Mở của sổ chỉnh sửa thiết bị
    @FXML
    public void openEditWindow() {
        mainWindow.setDisable(true);
        editWindow.setVisible(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        idTextField.setText(device.getId());
        nameTextField.setText(device.getName());
        amountTextField.setText(String.valueOf(device.getAmount()));
        usableTextField.setText(String.valueOf(device.getUsable()));
        brokenTextField.setText(String.valueOf(device.getBroken()));
    }

    //Đóng Popup window
    @FXML
    public void closePopup() {
        addWindow.setVisible(false);
        editWindow.setVisible(false);
        mainWindow.setDisable(false);
        mainWindow.setEffect(null);
    }

    public void addDevice(ActionEvent event) throws SQLException {
        Device newDevice = new Device();
        newDevice.setId(idAddTextField.getText());
        newDevice.setName(nameAddTextField.getText());
        newDevice.setAmount(Integer.parseInt(amountAddTextField.getText()));
        deviceList.add(newDevice);
        if (newDevice.getBroken() != 0)
            brokenList.add(newDevice);
        newDevice.addDevice();
        closePopup();
    }

    public void deleteDevice(ActionEvent event) throws SQLException {
        deviceList.remove(device);
        if (device.getBroken() != 0)
            brokenList.remove(device);
        device.deleteDevice();
        closePopup();
    }

    //Lưu lại thay đổi thiết bị
    public void applyChanges(ActionEvent event) throws SQLException {
        Device updateDevice = new Device();
        updateDevice.setId(idTextField.getText());
        updateDevice.setName(nameTextField.getText());
        updateDevice.setAmount(Integer.parseInt(amountTextField.getText()));
        updateDevice.setUsable(Integer.parseInt(usableTextField.getText()));
        updateDevice.setBroken(Integer.parseInt(brokenTextField.getText()));

        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i).equals(device)) {
                deviceList.set(i, updateDevice);
                updateDevice.updateDevice();
            }
        }

        if (updateDevice.getBroken() != 0) {
            boolean check = false;
            for (int i = 0; i < brokenList.size(); i++) {
                if (brokenList.get(i).equals(device)) {
                    brokenList.set(i, updateDevice);
                    check = true;
                }
            }
            if (!check) brokenList.add(updateDevice);
        } else {
            for (int i = 0; i < brokenList.size(); i++) {
                if (brokenList.get(i).equals(device)) {
                    brokenList.remove(device);
                }
            }
        }

        closePopup();
    }

    @FXML
    public void switchTable(ActionEvent event) {
        if (allDevicesTable.isVisible()) {
            switchButton.setText("All Device");
            allDevicesTable.setVisible(false);
            brokenDevicesTable.setVisible(true);
        } else {
            switchButton.setText("Broken Device");
            allDevicesTable.setVisible(true);
            brokenDevicesTable.setVisible(false);
        }
    }
}
