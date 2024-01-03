package teachingAidManagementSystem.classes;

import teachingAidManagementSystem.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Device {
    private String icon;
    private String id;
    private String name;
    private int amount;
    private int usable;
    private int broken;
    private String description;

    public Device(String icon, String id, String name, int amount, int usable, int broken) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.usable = usable;
        this.broken = broken;
    }

    public Device(String id, String name, int amount, int usable, int broken) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.usable = usable;
        this.broken = broken;
    }

    public Device() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.usable = amount;
        this.broken = 0;
    }

    public int getUsable() {
        return usable;
    }

    public void setUsable(int usable) {
        this.usable = usable;
    }

    public int getBroken() {
        return broken;
    }

    public void setBroken(int broken) {
        this.broken = broken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addDevice() throws SQLException {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String insertQuery = "INSERT INTO Device(ID, Name, Amount, Usable, Broken) VALUES " +
                "(N'" + id + "', N'" + name + "', " + amount + ", " + usable + ", " + broken + ")";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertQuery);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void deleteDevice() throws SQLException {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String deleteQuery = "DELETE FROM Device WHERE ID = '" + id + "'";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteQuery);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void updateDevice() throws SQLException {
        DatabaseConnection catConn = new DatabaseConnection();
        Connection connectDB = catConn.getConnection();

        String updateQuery = "UPDATE Device SET Name = N'" + name + "', Amount = " + amount +
                ", Usable = " + usable + ", Broken = " + broken + " WHERE ID = '" + id + "'";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(updateQuery);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
