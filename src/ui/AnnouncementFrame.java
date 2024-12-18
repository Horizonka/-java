package src.ui;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class AnnouncementFrame extends JFrame {
    private JTextArea announcementArea;
    private AnnouncementManager announcementManager;
    private String userRole;

    public AnnouncementFrame(String userRole) {
        this.userRole = userRole;
        announcementManager = new AnnouncementManager();
        initializeUI();
    }

    public AnnouncementFrame() {
        this("普通用户");
    }

    private void initializeUI() {
        setTitle("公告查看");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel announcementLabel = new JLabel("公告列表：");
        announcementLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        announcementArea = new JTextArea();
        announcementArea.setEditable(false);
        announcementArea.setLineWrap(true);
        announcementArea.setWrapStyleWord(true);
        announcementArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        displayAnnouncements();

        JScrollPane announcementScrollPane = new JScrollPane(announcementArea);
        announcementScrollPane.setPreferredSize(new Dimension(600, 300));

        JPanel publishPanel = new JPanel();
        if ("管理员".equals(userRole)) {
            publishPanel.setLayout(new GridBagLayout());
            publishPanel.setBorder(BorderFactory.createTitledBorder("发布新公告"));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel titleLabel = new JLabel("标题：");
            titleLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            JTextField titleField = new JTextField();

            JLabel contentLabel = new JLabel("内容：");
            contentLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            JTextArea contentArea = new JTextArea(5, 20);
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            JScrollPane contentScrollPane = new JScrollPane(contentArea);

            JButton publishButton = new JButton("发布公告");
            publishButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
            publishButton.addActionListener(e -> {
                String title = titleField.getText().trim();
                String content = contentArea.getText().trim();
                if (title.isEmpty() || content.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "标题或内容不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Announcement announcement = new Announcement(title, content, new Date());
                announcementManager.addAnnouncement(announcement);
                JOptionPane.showMessageDialog(this, "公告发布成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                titleField.setText("");
                contentArea.setText("");
                displayAnnouncements();
                System.out.println("Button clicked: " + e.getActionCommand());
            });

            gbc.gridx = 0;
            gbc.gridy = 0;
            publishPanel.add(titleLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            publishPanel.add(titleField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            publishPanel.add(contentLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            publishPanel.add(contentScrollPane, gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            publishPanel.add(publishButton, gbc);
        }

        JButton closeButton = new JButton("关闭");
        closeButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        closeButton.addActionListener(_ -> dispose());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(closeButton);

        mainPanel.add(announcementLabel, BorderLayout.NORTH);
        mainPanel.add(announcementScrollPane, BorderLayout.CENTER);
        if ("管理员".equals(userRole)) {
            mainPanel.add(publishPanel, BorderLayout.EAST);
        }
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        displayAnnouncements();
    }

    private void displayAnnouncements() {
        List<Announcement> announcements = announcementManager.getAnnouncements();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        for (Announcement announcement : announcements) {
            sb.append("标题: ").append(announcement.getTitle()).append("\n")
              .append("内容: ").append(announcement.getContent()).append("\n")
              .append("时间: ").append(dateFormat.format(announcement.getDate())).append("\n\n");
        }
        announcementArea.setText(sb.toString());
    }
}