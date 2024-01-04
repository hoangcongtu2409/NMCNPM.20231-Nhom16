package teachingAidManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import teachingAidManagementSystem.App;

import java.io.IOException;

public class ClientsUIController {
    @FXML
    private AnchorPane mainWindow;

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
}
