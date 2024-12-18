package src.ui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DeviceManager {
      private static final Logger logger = Logger.getLogger(DeviceManager.class.getName());
    private static final String URL = "jdbc:sqlite:library.db"; // SQLite数据库路径

    final private List<Device> devices;

    public DeviceManager() {
        devices = new ArrayList<>();
    }
   
    public void addDevice(Device device) {
        // 将设备添加到内存列表
        devices.add(device);
    
        // 将设备添加到数据库
        String query = "INSERT INTO devices (name, type, status) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, device.getName());
            pstmt.setString(2, device.getType());
            pstmt.setString(3, device.getStatus());
            pstmt.executeUpdate();
    
            // 获取数据库自动生成的id
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    device.setId(generatedId); // 更新内存中设备的id
                }
            }
        } catch (SQLException e) {
             logger.log(Level.SEVERE, "Error adding Device: ", e);
        }
    }
    
   

   

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<>();
        String query = "SELECT * FROM devices";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String status = rs.getString("status");
                devices.add(new Device(id, name, type, status));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting Device: ", e);
        }
        return devices;
    }

    public void updateDevice(int id,String name, String type, String status) {
        Device device = new Device(id, name, type, status);
        String query = "UPDATE devices SET name = ?, type = ?, status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, device.getName());
            pstmt.setString(2, device.getType());
            pstmt.setString(3, device.getStatus());
            pstmt.setInt(4, device.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating Device: ", e);
        }
    }

    public void deleteDevice(int id) {
        String query = "DELETE FROM devices WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting Device: ", e);
        }
    }

    // public List<Device> searchDevices(String keyword) {
    //     List<Device> devices = new ArrayList<>();
    //     String query = "SELECT * FROM devices WHERE name LIKE ? OR type LIKE ? OR status LIKE ?";
    //     try (Connection conn = DriverManager.getConnection(URL);
    //          PreparedStatement pstmt = conn.prepareStatement(query)) {
    //         String searchKeyword = "%" + keyword + "%";
    //         pstmt.setString(1, searchKeyword);
    //         pstmt.setString(2, searchKeyword);
    //         pstmt.setString(3, searchKeyword);
    //         ResultSet rs = pstmt.executeQuery();
    //         while (rs.next()) {
    //             int id = rs.getInt("id");
    //             String name = rs.getString("name");
    //             String type = rs.getString("type");
    //             String status = rs.getString("status");
    //             devices.add(new Device(id, name, type, status));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return devices;
    // }
} 