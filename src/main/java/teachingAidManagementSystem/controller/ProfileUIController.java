package teachingAidManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.classes.Profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileUIController implements Initializable {
    @FXML
    private TextField nameTextField;

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
        nameTextField.setText(LoginUIController.admin.getUsername());
    }
}
