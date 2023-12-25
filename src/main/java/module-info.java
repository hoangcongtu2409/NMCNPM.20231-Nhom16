module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens teachingAidManagementSystem to javafx.fxml;
    exports teachingAidManagementSystem;
    exports teachingAidManagementSystem.Controller;
    opens teachingAidManagementSystem.Controller to javafx.fxml;
}