package teachingAidManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection{
    public Connection databaseLink;
    public Connection getConnection() {
        String dbName = "DeviceManagement";
        String dbUser = "sa";
        String dbPassword = "sa";     //Pass này mỗi người tự đặt khác nhau
        String url = "jdbc:sqlserver://localhost" + ";databaseName=" + dbName;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            databaseLink = DriverManager.getConnection(url+";encrypt=true;trustServerCertificate=true;", dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
