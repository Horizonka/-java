package src.ui;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
public class UserMainFrame extends JFrame {
    private BufferedImage image;
    private String userRole;
     private static final Logger logger = Logger.getLogger(UserMainFrame.class.getName());
    public UserMainFrame(String username, String userRole) {
        this.userRole = userRole;
        setTitle("用户主界面 - 欢迎 " + username);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建背景面板，重写paintComponent方法绘制背景图像
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // 只加载一次图像
                if (image == null) {
                    try {
                        URL imageUrl = getClass().getResource("/resources/zhangwei.png");
                        if (imageUrl == null) {
                            System.err.println("资源文件未找到");
                            return;
                        }
                        image = ImageIO.read(imageUrl);
                    } catch (IOException e) {
                         logger.log(Level.SEVERE, "Error image: ", e);
                    }
                }

                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // 调整图像大小以适应面板
                }
            }
        };
        mainPanel.setLayout(new BorderLayout()); // 使用BorderLayout布局

        // 添加欢迎标签
        JLabel welcomeLabel = new JLabel("欢迎使用校园网络设备管理系统", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(70, 130, 180));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // 创建按钮面板并放置在底部
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 居中布局

        // 创建查看公告按钮
        JButton viewAnnouncementsButton = createStyledButton("查看公告");
        viewAnnouncementsButton.addActionListener(_ -> {
            AnnouncementFrame announcementFrame = new AnnouncementFrame(userRole);
            announcementFrame.setVisible(true);
        });

        // 创建设备管理按钮
        JButton manageDevicesButton = createStyledButton("设备管理");
        manageDevicesButton.addActionListener(_ -> {
            DeviceFrame deviceFrame = new DeviceFrame(userRole);
            deviceFrame.setVisible(true);
        });

        // 创建退出登录按钮
        JButton logoutButton = createStyledButton("退出登录");
        logoutButton.addActionListener(_ -> handleLogout());

        // 将按钮添加到底部面板
        bottomPanel.add(viewAnnouncementsButton);
        bottomPanel.add(manageDevicesButton);
        bottomPanel.add(logoutButton);

        // 将底部面板添加到主面板
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 将主面板添加到窗口
        add(mainPanel);
    }

    // 创建美化按钮的方法
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 150), 2));
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 150, 200));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    // 退出登录的方法
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this, "确定要退出登录吗？", "退出登录", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            // 退出登录，返回登录页面
            LoginFrame loginFrame = new LoginFrame(new DatabaseManager()); // 假设你已经有一个DatabaseManager实例
            loginFrame.setVisible(true);
            this.dispose(); // 关闭当前用户主界面
        }
    }
}
