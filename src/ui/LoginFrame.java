package src.ui;

import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    final private DatabaseManager dbManager;

    public LoginFrame(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("设备管理系统登录");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    
        // 主面板，带渐变背景
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(200, 225, 255), 0, getHeight(), new Color(150, 180, 255)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // 标题部分
        JLabel titleLabel = new JLabel("欢迎使用设备管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
        titleLabel.setForeground(new Color(60, 90, 180));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
    
        // 添加标语到中间区域，限制为两行
        JLabel sloganLabel = new JLabel("<html><div style='text-align: center;'>网络安全为人民 网络安全靠人民<br> 遵守法律法规 共建和谐校园环境</div></html>");
        sloganLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sloganLabel.setForeground(new Color(50, 50, 50));
        sloganLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(sloganLabel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    
        // 表单和按钮部分
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
    
        // 表单面板
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("用户名:"), gbc);
    
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("密码:"), gbc);
    
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);
    
        bottomPanel.add(formPanel);
    
        bottomPanel.add(Box.createVerticalStrut(20));
    
        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton loginButton = new JButton("登录");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
    
        JButton registerButton = new JButton("注册");
        registerButton.setBackground(new Color(100, 180, 100));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
    
        loginButton.addActionListener(_ -> handleLogin());
        registerButton.addActionListener(_ -> showRegisterDialog());
    
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(registerButton);
    
        bottomPanel.add(buttonPanel);
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    
        add(mainPanel);
    
        // 添加回车键登录功能
        usernameField.addActionListener(_ -> handleLogin());
        passwordField.addActionListener(_ -> handleLogin());
    }
    
     
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (dbManager.validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "登录成功！");
            String role = dbManager.getUserRole(username);
            UserMainFrame userMainFrame = new UserMainFrame(username, role);
            userMainFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisterDialog() {
        JDialog registerDialog = new JDialog(this, "注册", true);
        registerDialog.setSize(350, 250);
        registerDialog.setLocationRelativeTo(this);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(new JLabel("用户:"), gbc);
        JTextField registerUsernameField = new JTextField(15);
        gbc.gridx = 1;
        registerPanel.add(registerUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(new JLabel("密码:"), gbc);
        JPasswordField registerPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        registerPanel.add(registerPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(new JLabel("角色:"), gbc);
        String[] roles = {"管理员", "学生", "教师"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        registerPanel.add(roleComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(_ -> {
            String username = registerUsernameField.getText();
            String password = new String(registerPasswordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            dbManager.registerUser(username, password, role);
            JOptionPane.showMessageDialog(registerDialog, "注册成功！");
            registerDialog.dispose();
        });
        registerPanel.add(submitButton, gbc);

        registerDialog.add(registerPanel);
        registerDialog.setVisible(true);
    }
}
