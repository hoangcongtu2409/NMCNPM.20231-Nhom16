module teachingAidManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens teachingAidManagementSystem to javafx.fxml;
    exports teachingAidManagementSystem;
    exports teachingAidManagementSystem.classes;
    exports teachingAidManagementSystem.controller;
    opens teachingAidManagementSystem.controller to javafx.fxml;
}