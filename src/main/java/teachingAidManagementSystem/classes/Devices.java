package teachingAidManagementSystem.classes;

public class Devices {
    private String icon;
    private String id;
    private String name;
    private int amount;
    private int usable;
    private int broken;
    private String description;

    public Devices(String icon, String id, String name, int amount, int usable, int broken) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.usable = usable;
        this.broken = broken;
    }

    public Devices(String id, String name, int amount, int usable, int broken) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.usable = usable;
        this.broken = broken;
    }

    public Devices() {
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
}
