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
import javafx.scene.text.Text;
import javafx.util.Callback;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.classes.Client;
import teachingAidManagementSystem.classes.Device;
import teachingAidManagementSystem.dbcontext.ClientDBContext;
import teachingAidManagementSystem.dbcontext.DeviceDBContext;
import teachingAidManagementSystem.dbcontext.ProvisionDBContext;
import teachingAidManagementSystem.model.ClientModel;
import teachingAidManagementSystem.model.DeviceModel;
import teachingAidManagementSystem.model.Provision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class ManageUIController implements Initializable {

    public Text provisionId;
    public Text provisionDeviceId;
    public Text provisionClientId;
    public Text provisionDeviceName;
    public Text provisionClientName;
    public Text provisionAmount;
    public Text provisionBorrowTime;
    public Text provisionReturnTime;
    public Text provisionStatus;
    public Button cancelButton;
    public Button returnButton;
    public AnchorPane mainWindow;
    public AnchorPane provisionDetailPopup;
    public AnchorPane addProvisionPopup;
    public Button cancelAddButton;
    public Button openAddProvisionPopupBtn;
    public Button addNewProvisionButton;
    public TextField inputBorrowCourse;
    public TextField inputBorrowDate;
    public TextField inputReturnCourse;
    public TextField inputReturnDate;
    public TextField inputAmount;
    public ComboBox deviceComboBox;
    public ComboBox clientComboBox;
    private ObservableList<String> clientModels;
    private ObservableList<String> deviceModels;

    @FXML
    private TableView<Provision> provisionTable;

    @FXML
    private TableColumn<Provision, Integer> idProvisionColumn;

    @FXML
    private TableColumn<Provision, String> deviceProvisionColumn;

    @FXML
    private TableColumn<Provision, Integer> amountProvisionColumn;

    @FXML
    private TableColumn<Provision, Integer> clientProvisionColumn;

    @FXML
    private TableColumn<Provision, Void> actionProvisionColumn;
    private ProvisionDBContext provisionDB;
    private DeviceDBContext deviceDB;
    private ClientDBContext clientDB;
    private ObservableList<Provision> provisions;
    private Provision provision;

    @FXML
    private AnchorPane returnWindow;
    @FXML
    private Label provisionId1;
    @FXML
    private TextField brokenDevice;
    @FXML
    private Label allDevice;
    @FXML
    private TextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idProvisionColumn.setCellValueFactory(new PropertyValueFactory<Provision, Integer>("provisionID"));
        deviceProvisionColumn.setCellValueFactory(new PropertyValueFactory<Provision, String>("deviceID"));
        amountProvisionColumn.setCellValueFactory(new PropertyValueFactory<Provision, Integer>("amount"));
        clientProvisionColumn.setCellValueFactory(new PropertyValueFactory<Provision, Integer>("clientID"));

        provisionDB = new ProvisionDBContext();
        deviceDB = new DeviceDBContext();
        clientDB = new ClientDBContext();

        provisions = FXCollections.observableArrayList();

        provisions.addAll(provisionDB.list());
        provisionTable.setItems(provisions);


        addEditButton();
    }

    private void addEditButton() {
        Callback<TableColumn<Provision, Void>, TableCell<Provision, Void>> cellFactory
                = new Callback<TableColumn<Provision, Void>, TableCell<Provision, Void>>() {
            @Override
            public TableCell<Provision, Void> call(final TableColumn<Provision, Void> provisionsVoidTableColumn) {
                return new TableCell<Provision, Void>() {
                    final Button btn = new Button("Detail");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(e -> {
                                TableRow<Provision> row = getTableRow();
                                if (row != null) {
                                    provision = row.getItem();
                                    showDetailPopup();
                                }
                            });
                            setGraphic(btn);
                            setAlignment(javafx.geometry.Pos.CENTER);
                        }
                    }
                };
            }
        };
        actionProvisionColumn.setCellFactory(cellFactory);
    }

    public void showDetailPopup() {
        provisionId.setText(String.valueOf(provision.getProvisionID()));
        provisionDeviceId.setText(provision.getDeviceID());
        provisionDeviceName.setText(provision.getDeviceName());
        provisionClientId.setText(String.valueOf(provision.getClientID()));
        provisionClientName.setText(provision.getClientName());
        provisionAmount.setText(String.valueOf(provision.getAmount()));
        provisionBorrowTime.setText("Course " + provision.getBorrowCourse() + " " + provision.getBorrowDate());
        provisionReturnTime.setText("Course " + provision.getReturnCourse() + " " + provision.getReturnDate());
        if (LocalDate.now().isAfter(provision.getReturnDate().toLocalDate()))
            provisionStatus.setText("Late");
        else
            provisionStatus.setText("Waiting");
        provisionDetailPopup.setVisible(true);
        mainWindow.setDisable(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
    }

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

    public void closeProvisionDetailPopup() {
        provisionDetailPopup.setVisible(false);
        mainWindow.setDisable(false);
        mainWindow.setEffect(null);
    }

    public void closeMakeProvisionPopup() {
        addProvisionPopup.setVisible(false);
        mainWindow.setDisable(false);
        mainWindow.setEffect(null);
    }

    public void openAddProvisionPopup(ActionEvent actionEvent) {
        clientModels = FXCollections.observableArrayList();
        ArrayList<ClientModel> clients = clientDB.list();
        ArrayList<String> clientsName = new ArrayList<>();
        for(ClientModel c : clients)
            clientsName.add(c.getName());
        clientModels.addAll(clientsName);
        clientComboBox.setValue("Clients");
        clientComboBox.setItems(clientModels);

        deviceModels = FXCollections.observableArrayList();
        ArrayList<String> devicesName = new ArrayList<>();
        ArrayList<DeviceModel> devices = deviceDB.list();
        for(DeviceModel d : devices)
            devicesName.add(d.getName());
        deviceModels.addAll(devicesName);
        deviceComboBox.setValue("Devices");
        deviceComboBox.setItems(deviceModels);

        inputBorrowCourse.setText(null);
        inputBorrowDate.setText(String.valueOf(LocalDate.now()));
        inputReturnCourse.setText(null);
        inputReturnDate.setText(String.valueOf(LocalDate.now()));
        inputAmount.setText(null);

        addProvisionPopup.setVisible(true);
        mainWindow.setDisable(true);
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainWindow.setEffect(blur);
        returnWindow.setVisible(false);
        provisionDetailPopup.setVisible(false);
    }

    public void addNewProvision(ActionEvent actionEvent) {
        provision = new Provision();
        ArrayList<ClientModel> clients = clientDB.list();
        ClientModel client = new ClientModel();
        for(ClientModel c : clients)
            if(c.getName().equals(clientComboBox.getValue()))
                client = c;
        ArrayList<DeviceModel> devices = deviceDB.list();
        DeviceModel device = new DeviceModel();
        for(DeviceModel d : devices)
            if(d.getName().equals(deviceComboBox.getValue()))
                device = d;
        System.out.println(device.getName());
        System.out.println(client.getName());
        provision.setDeviceID(device.getId());
        provision.setClientID(client.getId());
        provision.setBorrowCourse(Integer.parseInt(inputBorrowCourse.getText()));
        provision.setBorrowDate(java.sql.Date.valueOf(inputBorrowDate.getText()));
        provision.setReturnCourse(Integer.parseInt(inputReturnCourse.getText()));
        provision.setReturnDate(java.sql.Date.valueOf(inputReturnDate.getText()));
        provision.setAmount(Integer.parseInt(inputAmount.getText()));

        try {
            if(device.getUsable() >= provision.getAmount()) {
                deviceDB = new DeviceDBContext();
                device.setUsable(device.getUsable() - provision.getAmount());
                deviceDB.update(device);
            }

            provisionDB.insert(provision);
            provision.setProvisionID(provisionDB.getLatestID());
            provisions.add(provision);

            closeMakeProvisionPopup();
        } catch (SQLException e) {
            // Kiểm tra xem lỗi có phải là "Insufficient amount of usable devices" hay không
            if (e.getErrorCode() == 51000) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Insufficient amount of usable devices");
                alert.showAndWait();
            } else {
                // Xử lý các lỗi khác theo cách thích hợp
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void openReturnDevice() {
        provisionDetailPopup.setVisible(false);
        returnWindow.setVisible(true);
        provisionId1.setText("Provision #" + String.valueOf(provision.getProvisionID()));
        allDevice.setText("/ " + String.valueOf(provision.getAmount()));
        brokenDevice.setText(null);
        description.setText(null);
    }

    @FXML
    public void cancelReturnDevice() {
        provisionDetailPopup.setVisible(true);
        returnWindow.setVisible(false);
    }

    @FXML
    public void confirmReturnDevice() throws SQLException {
        provision.setBroken(Integer.parseInt(brokenDevice.getText()));
        provision.setDescription(description.getText());
        provisionDB.delete(provision);
        provisions.remove(provision);
        returnWindow.setVisible(false);
        closeProvisionDetailPopup();
    }
}

// Hoang.NV215384
// 123456qweasd
