package teachingAidManagementSystem.dbcontext;

import teachingAidManagementSystem.DatabaseConnection;
import teachingAidManagementSystem.model.DeviceModel;
import teachingAidManagementSystem.model.Provision;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProvisionDBContext extends BaseDBContext<Provision> {

    private Provision convert(ResultSet rs)
    {
        try {
            return new Provision(
                    rs.getInt("ProvisionID"),
                    rs.getInt("ClientID"),
                    rs.getString("DeviceID"),
                    rs.getInt("BorrowCourse"),
                    rs.getDate("BorrowDate"),
                    rs.getInt("ReturnCourse"),
                    rs.getDate("ReturnDate"),
                    rs.getInt("Amount")
            );
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    @Override
    public ArrayList<Provision> list() {
        ArrayList<Provision> users = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from Provision"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(convert(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    @Override
    public Provision get(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from Provision where ProvisionID = " + id
            );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return convert(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public int getLatestID() {
        int provisionID = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select top 1 ProvisionID from Provision order by ProvisionID DESC"
            );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ProvisionID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provisionID;
    }

    @Override
    public Provision insert(Provision provision) throws SQLException {
        PreparedStatement statement = null;
            String sql = "INSERT INTO [Provision]\n"
                    + "           ([ClientID]\n"
                    + "           ,[DeviceID]\n"
                    + "           ,[BorrowCourse]\n"
                    + "           ,[BorrowDate]\n"
                    + "           ,[ReturnCourse]\n"
                    + "           ,[ReturnDate]\n"
                    + "           ,[Amount])\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, provision.getClientID());
            statement.setString(2, provision.getDeviceID());
            statement.setInt(3, provision.getBorrowCourse());
            statement.setDate(4, provision.getBorrowDate());
            statement.setInt(5, provision.getReturnCourse());
            statement.setDate(6, provision.getReturnDate());
            statement.setInt(7, provision.getAmount());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())  return provision;

        return null;
    }

    @Override
    public void update(Provision model) {

    }

    @Override
    public void delete(Provision provision) throws SQLException {
        // trigger add return device
        DeviceDBContext deviceDB = new DeviceDBContext();
        DeviceModel device = deviceDB.get(provision.getDeviceID());
        device.setBroken(device.getBroken() + provision.getBroken());
        device.setUsable(device.getUsable() + provision.getAmount() - provision.getBroken());
        deviceDB.update(device);

        try {
            String sql = "DELETE FROM [Provision]\n"
                    + "WHERE ProvisionID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, provision.getProvisionID());
            statement.executeUpdate();
            System.out.println("Deleted Provision: " + provision.getProvisionID());
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
