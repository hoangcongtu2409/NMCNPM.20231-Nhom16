package teachingAidManagementSystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.Classes.Devices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DevicesUIController {
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

    // Điều khiển TableView
    @FXML
    private TableView<Devices> table;
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
    private ObservableList<Devices> devicesList;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        devicesList = FXCollections.observableArrayList(
            new Devices("MIC", "Micro", 22, 12, 3)
        );
        idColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>( "ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Devices, String>( "Name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>( "Amount"));
        usableColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>( "Usable"));
        brokenColumn.setCellValueFactory(new PropertyValueFactory<Devices, Integer>( "Broken"));
        table.setItems(devicesList);
    }

}
