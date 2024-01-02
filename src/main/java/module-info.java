module teachingAidManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires java.desktop;
    requires com.microsoft.sqlserver.jdbc;

    opens teachingAidManagementSystem to javafx.fxml;
    exports teachingAidManagementSystem;
    exports teachingAidManagementSystem.classes;
    exports teachingAidManagementSystem.controller;
    opens teachingAidManagementSystem.controller to javafx.fxml;
}