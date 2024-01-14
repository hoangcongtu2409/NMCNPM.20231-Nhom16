package teachingAidManagementSystem.dbcontext;

import teachingAidManagementSystem.classes.Device;
import teachingAidManagementSystem.model.DeviceModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceDBContext extends BaseDBContext<DeviceModel> {
    private DeviceModel convert(ResultSet rs)
    {
        try {
            return new DeviceModel(
                rs.getString("ID"), rs.getString("Name"),
                    rs.getInt("Amount"), rs.getInt("Usable"),
                    rs.getInt("Broken"), rs.getString("Description")

            );
        } catch (SQLException ex) {
            Logger.getLogger(DeviceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public ArrayList<DeviceModel> list() {
        ArrayList<DeviceModel> devices = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from Device"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                devices.add(convert(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return devices;
    }

    public DeviceModel get(String id)
    {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from [Device] where [ID] = '" + id + "'"
            );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return convert(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeviceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public DeviceModel get(int id) {
        return null;
    }

    @Override
    public DeviceModel insert(DeviceModel model) {
        return null;
    }

    @Override
    public void update(DeviceModel device) {
        PreparedStatement statement = null;
        try {
            String sql = "UPDATE [Device]\n"
                    + " SET [Usable] = ?\n"
                    + " ,[Broken] = ?\n"
                    + " WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, device.getUsable());
            statement.setInt(2, device.getBroken());
            statement.setString(3, device.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DeviceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DeviceDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DeviceDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(DeviceModel mode) throws SQLException {

    }

    public static void main(String[] args) {
        DeviceDBContext deviceDB = new DeviceDBContext();
        System.out.println(deviceDB.get("B003").getName());
        DeviceModel device = deviceDB.get("B003");
        System.out.println("Usable: " + device.getUsable());
        device.setUsable(device.getUsable() + 5);
        deviceDB.update(device);
        device = null;
        deviceDB = new DeviceDBContext();
        device = deviceDB.get("B003");
        System.out.println("Usable :" + device.getUsable());
    }
}
