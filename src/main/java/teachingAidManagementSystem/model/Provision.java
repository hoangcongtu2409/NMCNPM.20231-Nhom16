package teachingAidManagementSystem.model;

import teachingAidManagementSystem.dbcontext.ClientDBContext;
import teachingAidManagementSystem.dbcontext.DeviceDBContext;

import java.sql.Date;
import java.util.ArrayList;

public class Provision extends BaseModel {
    private int provisionID;
    private int clientID;
    private String clientName;
    private String deviceID;
    private String deviceName;
    private int borrowCourse;
    private Date borrowDate;
    private int returnCourse;
    private Date returnDate;
    private int amount;
    private int broken;
    private String description;

    public Provision()
    {

    }

    public Provision(int provisionID, int clientID, String deviceID, int borrowCourse, Date borrowDate,
                     int returnCourse, Date returnDate, int amount)
    {
        this.provisionID = provisionID;
        this.clientID = clientID;
        this.deviceID = deviceID;
        this.borrowCourse = borrowCourse;
        this.borrowDate = borrowDate;
        this.returnCourse = returnCourse;
        this.returnDate = returnDate;
        this.amount = amount;
    }

    public int getProvisionID() {
        return provisionID;
    }

    public void setProvisionID(int provisionID) {
        this.provisionID = provisionID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        ClientDBContext clientDB = new ClientDBContext();
        ArrayList<ClientModel> clients = clientDB.list();
        for(ClientModel c : clients)
            if(c.getId() == clientID)
                clientName = c.getName();
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        DeviceDBContext deviceDB = new DeviceDBContext();
        ArrayList<DeviceModel> devices = deviceDB.list();
        for(DeviceModel d : devices)
            if(d.getId().equals(deviceID))
                deviceName = d.getName();
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getBorrowCourse() {
        return borrowCourse;
    }

    public void setBorrowCourse(int borrowCourse) {
        this.borrowCourse = borrowCourse;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getReturnCourse() {
        return returnCourse;
    }

    public void setReturnCourse(int returnCourse) {
        this.returnCourse = returnCourse;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
}
