package teachingAidManagementSystem.dbcontext;

import teachingAidManagementSystem.classes.Client;
import teachingAidManagementSystem.model.ClientModel;
import teachingAidManagementSystem.model.DeviceModel;
import teachingAidManagementSystem.model.Provision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDBContext extends BaseDBContext<ClientModel> {
    private ClientModel convert(ResultSet rs)
    {
        try {
            return new ClientModel(
                    rs.getInt("ClientID"), rs.getString("Name"),
                    rs.getString("Email"), rs.getString("Department"),
                    rs.getString("PhoneNumber")
            );
        } catch (SQLException ex) {
            Logger.getLogger(ClientDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public ArrayList<ClientModel> list() {
        ArrayList<ClientModel> clients = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from Client"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(convert(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clients;
    }

    @Override
    public ClientModel get(int id) {
        return null;
    }

    @Override
    public ClientModel insert(ClientModel model) {
        return null;
    }

    @Override
    public void update(ClientModel model) {

    }

    @Override
    public void delete(int id) {

    }

    public static void main(String[] args) {
        ClientDBContext clientDB = new ClientDBContext();
        ArrayList<ClientModel> clients = clientDB.list();
        for(ClientModel c : clients)
            System.out.println(c.getId());
    }
}
