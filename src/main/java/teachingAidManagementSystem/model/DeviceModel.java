package teachingAidManagementSystem.model;

public class DeviceModel extends BaseModel{
    private String id;
    private String name;
    private int amount;
    private int usable;
    private int broken;
    private String desc;

    public DeviceModel() {

    }

    public DeviceModel(String id, String name, int amount, int usable, int broken, String des)
    {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.usable = usable;
        this.broken = broken;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
