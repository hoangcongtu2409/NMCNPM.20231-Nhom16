package teachingAidManagementSystem.dbcontext;

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

    @Override
    public DeviceModel get(int id) {
        return null;
    }

    @Override
    public DeviceModel insert(DeviceModel model) {
        return null;
    }

    @Override
    public void update(DeviceModel model) {

    }

    @Override
    public void delete(DeviceModel mode) throws SQLException {

    }
}
