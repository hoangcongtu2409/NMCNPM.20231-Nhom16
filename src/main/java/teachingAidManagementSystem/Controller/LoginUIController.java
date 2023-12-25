package teachingAidManagementSystem.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import teachingAidManagementSystem.App;

import java.io.IOException;

public class LoginUIController {
    @FXML
    private TextField accField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Button signinButton;
    @FXML
    public void switchToHome() throws IOException {
        accField.getText();
        pwField.getText();
        App.setRoot("homeUI");
    }
}
