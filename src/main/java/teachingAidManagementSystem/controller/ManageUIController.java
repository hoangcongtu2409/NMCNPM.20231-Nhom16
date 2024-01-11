package teachingAidManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.classes.Device;
import teachingAidManagementSystem.dbcontext.ClientDBContext;
import teachingAidManagementSystem.dbcontext.DeviceDBContext;
import teachingAidManagementSystem.dbcontext.ProvisionDBContext;
import teachingAidManagementSystem.model.ClientModel;
import teachingAidManagementSystem.model.DeviceModel;
import teachingAidManagementSystem.model.Provision;

import java.io.IOException;
import java.net.URL;
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
    public AnchorPane provisionDetailPopup;
    public AnchorPane provisionTablePopup;
    public AnchorPane addProvisionPopup;
    public Button cancelAddButton;
    public Button openAddProvisionPopupBtn;
    public Button addNewProvisionButton;
    public TextField inputBorrowCourse;
    public TextField inputReturnCourse;
    public TextField inputAmount;
    public ComboBox deviceComboBox;
    public ComboBox clientComboBox;
    private ObservableList<Integer> clientModels;
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
        provision = new Provision();
        provision.setProvisionID(1);
        provision.setClientID(1);
        provision.setDeviceID("B003");
        provision.setAmount(1);
        provisions.add(provision);
        provisions.addAll(provisionDB.list());
        provisionTable.setItems(provisions);


        addEditButton();
        provisionTablePopup.setVisible(true);
        provisionTable.setVisible(true);
        provisionDetailPopup.setVisible(false);
        addProvisionPopup.setVisible(false);
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
                        }
                    }
                };
            }
        };
        actionProvisionColumn.setCellFactory(cellFactory);
    }

    private void showDetailPopup() {
        provisionId.setText(String.valueOf(provision.getProvisionID()));
        provisionDeviceId.setText(provision.getDeviceID());
        ArrayList<DeviceModel> devices = deviceDB.list();
        String deviceName = "deviceName";
        for(DeviceModel d : devices)
            if(d.getId().equals(provision.getDeviceID()))
                deviceName = d.getName();
        provisionDeviceName.setText(deviceName);
        provisionClientId.setText(String.valueOf(provision.getClientID()));
        ArrayList<ClientModel> clients = clientDB.list();
        String clientName = "clientName";
        for(ClientModel c : clients)
            if(c.getId() == provision.getClientID())
                clientName = c.getName();
        provisionClientName.setText(clientName);
        provisionAmount.setText(String.valueOf(provision.getAmount()));
        provisionStatus.setText("Waiting");
        provisionDetailPopup.setVisible(true);
        provisionTablePopup.setVisible(false);
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

    public void closeProvisionDetailPopup(ActionEvent actionEvent) {
        provisionDetailPopup.setVisible(false);
        provisionTablePopup.setVisible(true);
    }

    public void closeMakeProvisionPopup(ActionEvent actionEvent) {
        addProvisionPopup.setVisible(false);
        provisionTablePopup.setVisible(true);
    }

    public void openAddProvisionPopup(ActionEvent actionEvent) {
        clientModels = FXCollections.observableArrayList();
        ArrayList<ClientModel> clients = clientDB.list();
        ArrayList<Integer> clientsID = new ArrayList<>();
        for(ClientModel c : clients)
            clientsID.add(c.getId());
        clientModels.addAll(clientsID);
        clientComboBox.setValue("Clients");
        clientComboBox.setItems(clientModels);

        deviceModels = FXCollections.observableArrayList();
        ArrayList<String> devicesID = new ArrayList<>();
        ArrayList<DeviceModel> devices = deviceDB.list();
        for(DeviceModel d : devices)
            devicesID.add(d.getId());
        deviceModels.addAll(devicesID);
        deviceComboBox.setValue("Devices");
        deviceComboBox.setItems(deviceModels);

        addProvisionPopup.setVisible(true);
        provisionTablePopup.setVisible(false);
        provisionDetailPopup.setVisible(false);
    }

    public void addNewProvision(ActionEvent actionEvent) {
        provision = new Provision();
        provision.setProvisionID(2);
        provision.setClientID(1);
        provision.setDeviceID("IphoneX");
        provision.setBorrowCourse(1);
        provision.setBorrowDate(java.sql.Date.valueOf(LocalDate.now()));
        provision.setReturnCourse(2);
        provision.setReturnDate(java.sql.Date.valueOf(LocalDate.now()));
        provision.setAmount(2);
        provisions.add(provision);
        provisionTable.setItems(provisions);
        provisionTablePopup.setVisible(true);
        addProvisionPopup.setVisible(false);
    }
}

// Hoang.NV215384
// 123456qweasd
