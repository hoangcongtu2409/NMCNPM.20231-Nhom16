package teachingAidManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import teachingAidManagementSystem.App;
import teachingAidManagementSystem.DatabaseConnection;
import teachingAidManagementSystem.classes.Profile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginUIController {
    public static Profile admin = new Profile();
    @FXML
    private TextField accField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Button signinButton;
    @FXML
    public void switchToHome() throws IOException {
        String username = accField.getText();
        String password = pwField.getText();
        if (isValidLogin(username, password)) {
            App.setRoot("homeUI");
        } else {
            if (!username.isEmpty() && !password.isEmpty()) {
                showErrorAlert("Username or password is incorrect!");
            }
            else {
                String mess = "";
                if (username.isEmpty()) {
                    mess = mess + "Please fill in your username\n";
                }
                if (password.isEmpty()) {
                    mess = mess + "Please fill in your password\n";
                }
                showErrorAlert(mess);
            }
        }
    }

    private boolean isValidLogin(String username, String password) {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String check = "SELECT * FROM Profile WHERE Username = '" + username + "'";

        try {
            PreparedStatement statement = connectDB.prepareStatement(check);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                if (password.equals(rs.getString("Password"))) {
                    admin.setUsername(username);
                    admin.setPassword(password);
                    admin.setName(rs.getNString("Name"));
                    admin.setEmail(rs.getString("Email"));
                    admin.setPhone(rs.getString("Phone"));
                    admin.setAddress(rs.getNString("Address"));
                    admin.setImage((Image) rs.getObject("Image"));
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi Đăng nhập");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
