package src.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class DeviceFrame extends JFrame {

    private JTextArea deviceArea;
    private final DeviceManager deviceManager;
    private final String userRole;  // 添加用户角色变量

    public DeviceFrame(String userRole) {
        this.userRole = userRole;  // 在构造函数中传入用户角色
        deviceManager = new DeviceManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("设备管理");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("添加设备");
        JButton deleteButton = new JButton("删除设备");
        JButton updateButton = new JButton("修改设备");
        JButton statsButton = new JButton("设备状态统计");
        JButton searchButton = new JButton("查询设备");

        addButton.setPreferredSize(new Dimension(120, 30));
        deleteButton.setPreferredSize(new Dimension(120, 30));
        updateButton.setPreferredSize(new Dimension(120, 30));
        statsButton.setPreferredSize( new Dimension(120, 30)); // 设置按钮大小
        searchButton.setPreferredSize(new Dimension(120, 30)); // 设置按钮大小

        // 只有管理员可以进行增、删、改操作
        if ("管理员".equals(userRole)) {
            addButton.addActionListener(_ -> addDevice());
            deleteButton.addActionListener(_ -> showDeleteDevices());
            updateButton.addActionListener(_ -> showUpdateDevices());
            statsButton.addActionListener(_ -> showDeviceStatusStats()); 
            searchButton.addActionListener(_ -> searchDevices()); // 设置查询按钮的行为
        } else {
            // 普通用户只能查看，禁用按钮
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            updateButton.setEnabled(false);
            statsButton.setEnabled(false); // 普通用户禁用设备状态统计按钮
        }

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(searchButton);


        // 设备信息显示区域
        deviceArea = new JTextArea();
        deviceArea.setEditable(false);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(deviceArea), BorderLayout.CENTER);

        // 创建返回按钮并放置在右下角
        JButton returnButton = new JButton("返回");
        returnButton.setPreferredSize(new Dimension(120, 30));
        returnButton.addActionListener(_ -> returnToMainPage());

        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 右对齐
        returnPanel.add(returnButton);

        panel.add(returnPanel, BorderLayout.SOUTH); // 将返回按钮面板添加到南部

        add(panel);
        displayDevices();
    }
    private void searchDevices() {
    // 弹出输入框，获取用户输入的查询关键字
    String keyword = JOptionPane.showInputDialog(this, "请输入设备名称或状态进行搜索:");

    if (keyword != null && !keyword.trim().isEmpty()) {
        // 获取设备列表并过滤出符合条件的设备
        List<Device> devices = deviceManager.getDevices();
        List<Device> searchResults = new ArrayList<>();

        // 过滤设备列表，根据设备名称或状态进行匹配
        for (Device device : devices) {
            if (device.getName().contains(keyword) || device.getStatus().contains(keyword)) {
                searchResults.add(device);
            }
        }

        // 更新显示区域，显示查询结果
        StringBuilder resultText = new StringBuilder();
        for (Device device : searchResults) {
            resultText.append("ID: ").append(device.getId())
                      .append(", 名称: ").append(device.getName())
                      .append(", 类型: ").append(device.getType())
                      .append(", 状态: ").append(device.getStatus())
                      .append("\n");
        }

        if (searchResults.isEmpty()) {
            resultText.append("未找到符合条件的设备。");
        }

        deviceArea.setText(resultText.toString());  // 更新设备显示区域
    } else {
        JOptionPane.showMessageDialog(this, "请输入有效的查询条件！", "错误", JOptionPane.ERROR_MESSAGE);
    }
}
    private void showDeviceStatusStats() {
    // 获取设备状态数据，统计各状态的数量
    Map<String, Integer> statusCount = new HashMap<>();
    List<Device> devices = deviceManager.getDevices();

    // 统计设备状态
    for (Device device : devices) {
        String status = device.getStatus();
        statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
    }

    // 创建设备状态统计窗口
    JFrame statsFrame = new JFrame("设备状态统计");
    statsFrame.setSize(500, 400);
    statsFrame.setLocationRelativeTo(null);

    // 创建并展示条形图
    DeviceStatusChart chartPanel = new DeviceStatusChart(statusCount);
    statsFrame.add(chartPanel);
    statsFrame.setVisible(true);
    }

    private void returnToMainPage() {
        this.dispose();
    }

    private void addDevice() {
        // 创建输入面板
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 设置为网格布局：3行2列

        // 创建设备名称输入框
        JLabel nameLabel = new JLabel("设备名称:");
        JTextField nameField = new JTextField();

        // 创建设备类型输入框
        JLabel typeLabel = new JLabel("设备类型:");
        JTextField typeField = new JTextField();

        // 创建设备状态输入框
        JLabel statusLabel = new JLabel("设备状态:");
        JTextField statusField = new JTextField();

        // 将组件添加到输入面板
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(typeLabel);
        inputPanel.add(typeField);
        inputPanel.add(statusLabel);
        inputPanel.add(statusField);

        // 弹出对话框，显示输入面板
        int option = JOptionPane.showConfirmDialog(this, inputPanel, "请输入设备信息", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // 如果点击"OK"，则进行设备添加操作
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String type = typeField.getText().trim();
            String status = statusField.getText().trim();

            // 输入校验
            if (name.isEmpty() || type.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "所有字段必须填写！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 创建设备对象
            Device newDevice = new Device(name, type, status);

            // 调用设备管理器的addDevice方法，传入Device对象
            deviceManager.addDevice(newDevice);

            // 提示用户设备已成功添加
            JOptionPane.showMessageDialog(this, "设备已成功添加！", "成功", JOptionPane.INFORMATION_MESSAGE);

            // 刷新设备列表显示
            displayDevices();
        }
    }

    private void showDeleteDevices() {
        // 创建用于展示删除功能的窗口
        JFrame deleteFrame = new JFrame("删除设备");
        deleteFrame.setSize(400, 300);
        deleteFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 使用垂直布局展示设备列表
    
        // 获取设备列表
        List<Device> devices = deviceManager.getDevices();
    
        // 遍历设备，为每个设备添加删除按钮
        for (Device device : devices) {
            JPanel devicePanel = new JPanel(new BorderLayout()); // 每个设备独立一个小面板
            JLabel label = new JLabel("ID: " + device.getId() + " | 名称: " + device.getName());
            JButton deleteButton = new JButton("删除");
    
            // 添加删除按钮的点击事件
            deleteButton.addActionListener(_ -> {
                int confirm = JOptionPane.showConfirmDialog(deleteFrame,
                        "确定要删除设备：" + device.getName() + " 吗？",
                        "确认删除",
                        JOptionPane.YES_NO_OPTION);
    
                if (confirm == JOptionPane.YES_OPTION) {
                    // 调用设备管理类删除方法
                    deviceManager.deleteDevice(device.getId());
                    JOptionPane.showMessageDialog(deleteFrame, "设备 " + device.getName() + " 删除成功！");
                    deleteFrame.dispose(); // 关闭当前窗口
                    showDeleteDevices(); // 刷新删除设备窗口
                }
            });
    
            // 将设备信息和删除按钮加入面板
            devicePanel.add(label, BorderLayout.CENTER);
            devicePanel.add(deleteButton, BorderLayout.EAST);
    
            panel.add(devicePanel); // 将每个设备面板加入主面板
        }
    
        // 如果设备列表为空，显示提示信息
        if (devices.isEmpty()) {
            JLabel emptyLabel = new JLabel("当前没有可删除的设备！");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(emptyLabel);
        }
    
        // 滚动面板以支持更多设备显示
        JScrollPane scrollPane = new JScrollPane(panel);
        deleteFrame.add(scrollPane);
    
        // 显示窗口
        deleteFrame.setVisible(true);
        displayDevices();
    }

    private void showUpdateDevices() {
        JFrame updateFrame = new JFrame("修改设备");
        updateFrame.setSize(400, 400);
        updateFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); // 动态行数，2列

        List<Device> devices = deviceManager.getDevices();

        for (Device device : devices) {
            JLabel deviceLabel = new JLabel("ID: " + device.getId() + ", 名称: " + device.getName());
            JButton updateButton = new JButton("修改");

            // 为每个修改按钮添加动作监听器
            updateButton.addActionListener(_ -> {
                String newName = JOptionPane.showInputDialog(updateFrame, "请输入新的设备名称:", device.getName());
                String newType = JOptionPane.showInputDialog(updateFrame, "请输入新的设备类型:", device.getType());
                String newStatus = JOptionPane.showInputDialog(updateFrame, "请输入新的设备状态:", device.getStatus());

                if (newName != null && newType != null && newStatus != null && 
                    !newName.isEmpty() && !newType.isEmpty() && !newStatus.isEmpty()) {
                    deviceManager.updateDevice(device.getId(), newName, newType, newStatus);
                    JOptionPane.showMessageDialog(updateFrame, "设备已成功修改！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    updateFrame.dispose(); // 关闭修改窗口
                    displayDevices(); // 刷新主界面的设备显示
                } else {
                    JOptionPane.showMessageDialog(updateFrame, "所有字段必须填写！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            });

            panel.add(deviceLabel);
            panel.add(updateButton);
        }

        JScrollPane scrollPane = new JScrollPane(panel); // 支持滚动的面板
        updateFrame.add(scrollPane);
        updateFrame.setVisible(true);
        displayDevices();
    }

    private void displayDevices() {
        List<Device> devices = deviceManager.getDevices();
        StringBuilder deviceList = new StringBuilder();

        for (Device device : devices) {
            deviceList.append("ID: ").append(device.getId())
                      .append(", 名称: ").append(device.getName())
                      .append(", 类型: ").append(device.getType())
                      .append(", 状态: ").append(device.getStatus())
                      .append("\n");
        }

        deviceArea.setText(deviceList.toString());
    }
}


