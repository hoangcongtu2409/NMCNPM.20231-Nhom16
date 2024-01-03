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
import teachingAidManagementSystem.classes.Devices;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DevicesUIController implements Initializable {
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
    private TableView<Devices> allDevicesTable;
    @FXML
    private TableView<Devices> brokenDevicesTable;
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
    private TableColumn<Devices, String> descriptionColumn;
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
        devicesList = FXCollections.observableArrayList();
        getDataList();
        iconColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>("icon"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>("amount"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>("usable"));
        brokenColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>("broken"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>("description"));
        allDevicesTable.setItems(devicesList);
        brokenDevicesTable.setItems(devicesList);
        addButtonToTable();
    }

    //Lấy dữ liệu từ database
    private void getDataList() {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String selectAllData ="SELECT * FROM Devices";
        try {
            PreparedStatement statement = connectDB.prepareStatement(selectAllData);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Devices newDevice = new Devices();
                newDevice.setId(rs.getString("ID"));
                newDevice.setName(rs.getNString("Name"));
                newDevice.setAmount(rs.getInt("Amount"));
                newDevice.setUsable(rs.getInt("Usable"));
                newDevice.setBroken(rs.getInt("Broken"));
                newDevice.setDescription(rs.getNString("Description"));
                devicesList.add(newDevice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
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
        iconTextField.setText(device.getIcon());
        idTextField.setText(device.getId());
        nameTextField.setText(device.getName());
        amountTextField.setText(String.valueOf(device.getAmount()));
        usableTextField.setText(String.valueOf(device.getUsable()));
        brokenTextField.setText(String.valueOf(device.getBroken()));
    }

    //Đóng Popup window
    @FXML
    public void closePopup() {
        addAndEditWindow.setVisible(false);
        mainWindow.setEffect(null);
    }

    public void addDevice(ActionEvent event) throws SQLException {
        Devices newDevice = new Devices();
        newDevice.setIcon(iconTextField.getText());
        newDevice.setId(idTextField.getText());
        newDevice.setName(nameTextField.getText());
        newDevice.setAmount(Integer.parseInt(amountTextField.getText()));
        newDevice.setUsable(Integer.parseInt(usableTextField.getText()));
        newDevice.setBroken(Integer.parseInt(brokenTextField.getText()));
        devicesList.add(newDevice);
        newDevice.addDevice();
        closePopup();
    }

    public void deleteDevice(ActionEvent event) throws SQLException {
        devicesList.remove(device);
        device.deleteDevice();
        closePopup();
    }

    //Lưu lại thay đổi thiết bị
    public void applyChanges(ActionEvent event) throws SQLException {
        Devices updateDevice = new Devices();
        updateDevice.setIcon(iconTextField.getText());
        updateDevice.setId(idTextField.getText());
        updateDevice.setName(nameTextField.getText());
        updateDevice.setAmount(Integer.parseInt(amountTextField.getText()));
        updateDevice.setUsable(Integer.parseInt(usableTextField.getText()));
        updateDevice.setBroken(Integer.parseInt(brokenTextField.getText()));
        for (int i = 0; i < devicesList.size(); i++) {
            if (devicesList.get(i).equals(device)) {
                devicesList.set(i, updateDevice);
                updateDevice.updateDevice();
            }
        }
        closePopup();
    }

    @FXML
    public void switchTable(ActionEvent event) {
        if (allDevicesTable.isVisible()) {
            switchButton.setText("All Devices");
            allDevicesTable.setVisible(false);
            brokenDevicesTable.setVisible(true);
        } else {
            switchButton.setText("Broken Devices");
            allDevicesTable.setVisible(true);
            brokenDevicesTable.setVisible(false);
        }
    }
}
