package teachingAidManagementSystem.classes;

public class Devices {
    private String iconDevice;
    private String idDevice;
    private String nameDevice;
    private int amountDevice;
    private int usableDevice;
    private int brokenDevice;

    public Devices(String iconDevice, String idDevice, String nameDevice, int amountDevice, int usableDevice, int brokenDevice) {
        this.iconDevice = iconDevice;
        this.idDevice = idDevice;
        this.nameDevice = nameDevice;
        this.amountDevice = amountDevice;
        this.usableDevice = usableDevice;
        this.brokenDevice = brokenDevice;
    }

    public Devices(String idDevice, String nameDevice, int amountDevice, int usableDevice, int brokenDevice) {
        this.idDevice = idDevice;
        this.nameDevice = nameDevice;
        this.amountDevice = amountDevice;
        this.usableDevice = usableDevice;
        this.brokenDevice = brokenDevice;
    }

    public Devices() {
    }

    public String getIconDevice() {
        return iconDevice;
    }

    public void setIconDevice(String iconDevice) {
        this.iconDevice = iconDevice;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public int getAmountDevice() {
        return amountDevice;
    }

    public void setAmountDevice(int amountDevice) {
        this.amountDevice = amountDevice;
    }

    public int getUsableDevice() {
        return usableDevice;
    }

    public void setUsableDevice(int usableDevice) {
        this.usableDevice = usableDevice;
    }

    public int getBrokenDevice() {
        return brokenDevice;
    }

    public void setBrokenDevice(int brokenDevice) {
        this.brokenDevice = brokenDevice;
    }
}
