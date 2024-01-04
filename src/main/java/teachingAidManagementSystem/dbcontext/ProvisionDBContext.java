package teachingAidManagementSystem.dbcontext;

import teachingAidManagementSystem.model.Provision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProvisionDBContext extends BaseDBContext<Provision> {

    private Provision convert(ResultSet rs)
    {
        try {
            return new Provision(
                    rs.getInt("ProvisionID"),
                    rs.getString("ClientID"),
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

    @Override
    public Provision insert(Provision provision) {
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO [Provision]\n"
                    + "           ([ProvisionID]\n"
                    + "           ,[ClientID]\n"
                    + "           ,[DeviceID]\n"
                    + "           ,[borrowCourse]\n"
                    + "           ,[borrowDate]\n"
                    + "           ,[returnCourse]\n"
                    + "           ,[returnDate]\n"
                    + "           ,[amount])\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, provision.getProvisionID());
            statement.setString(2, provision.getClientID());
            statement.setString(3, provision.getDeviceID());
            statement.setInt(4, provision.getBorrowCourse());
            statement.setDate(5, provision.getBorrowDate());
            statement.setInt(6, provision.getReturnCourse());
            statement.setDate(7, provision.getReturnDate());
            statement.setInt(8, provision.getAmount());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                provision.setProvisionID(id);
                System.out.println("added provision-id: " + provision.getProvisionID());
                return provision;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProvisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public void update(Provision model) {

    }

    @Override
    public void delete(int id) {

    }

    public static void main(String[] args) {
        ProvisionDBContext provisionDBContext = new ProvisionDBContext();
        ArrayList<Provision> provisions = provisionDBContext.list();
        System.out.println(provisions.size());
    }
}
