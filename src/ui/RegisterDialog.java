package src.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class RegisterDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    final private DatabaseManager dbManager;

    public RegisterDialog(Frame parent, DatabaseManager dbManager) {
        super(parent, "用户注册", true);
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 450);
        setLocationRelativeTo(getParent());
        getContentPane().setBackground(new Color(240, 240, 245));

        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // 添加标题
        JLabel titleLabel = new JLabel("新用户注册");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // 用户名输入
        JLabel userLabel = new JLabel("用户名：");
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));

        // 密码输入
        JLabel passLabel = new JLabel("密码：");
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));

        // 角色选择
        JLabel roleLabel = new JLabel("角色：");
        roleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        String[] roles = {"学生", "教师", "管理员"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setPreferredSize(new Dimension(200, 30));
        roleComboBox.setBackground(Color.WHITE);

        // 添加组件到表单
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roleComboBox, gbc);

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 245));
        JButton registerButton = new JButton("注册");
        JButton cancelButton = new JButton("取消");

        // 美化按钮
        registerButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(95, 158, 160));
        cancelButton.setForeground(Color.WHITE);

        registerButton.addActionListener((ActionEvent _) -> {
            handleRegister();
        });
        
        cancelButton.addActionListener((ActionEvent _) -> {
            dispose();
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(cancelButton);

        // 添加所有组件到主面板
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonPanel);

        // 添加主面板到对话框
        add(mainPanel);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        System.out.println("注册信息: 用户名=" + username + ", 密码=" + password + ", 角色=" + role); // 调试信息

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "用户名和密码不能为空！", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dbManager.registerUser(username, password, role)) {
            JOptionPane.showMessageDialog(this, 
                "注册成功！", 
                "提示", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "注册失败，用户名可能已存在！", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}