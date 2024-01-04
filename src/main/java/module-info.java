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
    exports teachingAidManagementSystem.constant;
    opens teachingAidManagementSystem.constant to javafx.fxml;
    exports teachingAidManagementSystem.model;
    opens teachingAidManagementSystem.model to javafx.fxml;
    exports teachingAidManagementSystem.dbcontext;
    opens teachingAidManagementSystem.dbcontext to javafx.fxml;
}