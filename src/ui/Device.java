package src.ui;

public class Device {
    private int id;
    private final String name;
    private final String type;
    private final String status;

    public Device(int id, String name, String type, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
    }

    public Device(String name, String type, String status) {
        this.name = name;
        this.type = type;
        this.status = status;
    }

    @Override
    public String toString() {
        
        return "Device{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
 
    

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
} 