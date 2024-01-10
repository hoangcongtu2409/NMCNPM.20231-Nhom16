package teachingAidManagementSystem.classes;

import teachingAidManagementSystem.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {
    private int clientID;
    private String name;
    private String email;
    private String phoneNumber;
    private String department;

    public Client() {
    }

    public Client(int clientID, String name, String email, String phoneNumber, String department) {
        this.clientID = clientID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public Client(String name, String email, String phoneNumber, String department) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void addClient() throws SQLException {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String insertQuery = "INSERT INTO Client(Name, Email, Department, PhoneNumber) VALUES " +
                            "(N'" + name + "', '" + email + "', N'" + department + "', '" + phoneNumber + "')";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertQuery);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void deleteClient() {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String deleteQuery = "DELETE FROM Client WHERE ClientID = '" + clientID + "'";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteQuery);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void updateClient() {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String updateQuery = "UPDATE Client SET Name = N'" + name + "', Email = '" + email + "', " +
                "Department = N'" + department + "', PhoneNumber = '" + phoneNumber + "' WHERE ClientID = " + clientID;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(updateQuery);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
