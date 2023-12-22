package teachingAidManagementSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
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
