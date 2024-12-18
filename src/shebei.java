package src;

import javax.swing.*;
import src.ui.DatabaseManager;
import src.ui.LoginFrame;

public class shebei {
    public static void main(String[] args) {
        // 创建数据库管理器实例
        DatabaseManager dbManager = new DatabaseManager();

        // 创建登录窗口
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(dbManager);
            loginFrame.setVisible(true);
        });
    }
}
