package teachingAidManagementSystem.model;

import java.sql.Date;

public class Provision extends BaseModel {
    private int provisionID;
    private int clientID;
    private String deviceID;
    private int borrowCourse;
    private Date borrowDate;
    private int returnCourse;
    private Date returnDate;
    private int amount;

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

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
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
}
