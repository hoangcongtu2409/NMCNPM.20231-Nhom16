package teachingAidManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.DatabaseConnection;
import teachingAidManagementSystem.classes.Profile;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

import static teachingAidManagementSystem.controller.LoginUIController.admin;

public class ProfileUIController implements Initializable {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;

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
        nameTextField.setText(LoginUIController.admin.getName());
        emailTextField.setText(LoginUIController.admin.getEmail());
        phoneTextField.setText(LoginUIController.admin.getPhone());
        addressTextField.setText(LoginUIController.admin.getAddress());
    }

    @FXML
    public void editProfile() {
        nameTextField.setEditable(true);
        nameTextField.setStyle("-fx-background-color: black; -fx-view-order: 2");
        emailTextField.setEditable(true);
        emailTextField.setStyle("-fx-background-color: black; -fx-view-order: 2");
        phoneTextField.setEditable(true);
        AnchorPane.setRightAnchor(phoneTextField, 60.0);
        addressTextField.setEditable(true);
        AnchorPane.setRightAnchor(addressTextField, 60.0);
        editButton.setVisible(false);
        saveButton.setVisible(true);
    }

    public void saveChanges() {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String updateQuery = "UPDATE Profile SET Name = N'" + nameTextField.getText() + "', " +
                "Email = '" + emailTextField.getText() + "', " +
                "Address = N'" + addressTextField.getText() + "', " +
                "Phone = '" + phoneTextField.getText() + "' " +
                "WHERE Username = '" + LoginUIController.admin.getUsername() + "'";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(updateQuery);

        } catch (Exception e) {
            String mess = "";
            if (nameTextField.getText().trim().isEmpty()) {
                mess = mess + "Please fill in your name\n";
            }
            if (emailTextField.getText().trim().isEmpty()) {
                mess = mess + "Please fill in your address\n";
            }
            showErrorAlert(mess);
            return;

        }

        LoginUIController.admin.setName(nameTextField.getText());
        LoginUIController.admin.setEmail(emailTextField.getText());
        LoginUIController.admin.setPhone(phoneTextField.getText());
        LoginUIController.admin.setAddress(addressTextField.getText());

        nameTextField.setEditable(false);
        nameTextField.setStyle("-fx-background-color: black; -fx-view-order: 0");
        emailTextField.setEditable(false);
        emailTextField.setStyle("-fx-background-color: black; -fx-view-order: 0");
        phoneTextField.setEditable(false);
        AnchorPane.setRightAnchor(phoneTextField, 27.0);
        addressTextField.setEditable(false);
        AnchorPane.setRightAnchor(addressTextField, 27.0);
        editButton.setVisible(true);
        saveButton.setVisible(false);
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
