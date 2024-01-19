package teachingAidManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.classes.Profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static teachingAidManagementSystem.controller.LoginUIController.admin;

public class HomeUIController implements Initializable {
    @FXML
    private Label adminName;
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

    @FXML
    public void logout() throws IOException {
        admin = null;
        admin = new Profile();
        App.setRoot("loginUI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText("Welcome back, " + admin.getName() + "!");
    }
}
