package src.ui;
import javax.swing.*;
public class MainFrame {
    public static void main(String[] args) {
        // 创建一个 userRole 字符串并赋予一个值
        String userRole = "admin";  // 或者你可以根据需要设置为 "user" 或其他角色
        
        SwingUtilities.invokeLater(() -> {
            // 创建 DeviceFrame 对象时传入 userRole 作为参数
            DeviceFrame deviceFrame = new DeviceFrame(userRole);
            deviceFrame.setVisible(true);  // 显示窗口
        });
    }
}