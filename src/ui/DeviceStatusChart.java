 package src.ui;
 import java.awt.*;
 import java.util.Map;
 import javax.swing.*;
 
 public class DeviceStatusChart extends JPanel {

    final private Map<String, Integer> deviceStatus;

    public DeviceStatusChart(Map<String, Integer> deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int chartWidth = getWidth();
        int chartHeight = getHeight();
        
        // 计算每个条形的宽度和最大值
        int barWidth = chartWidth / deviceStatus.size();
        int maxStatusValue = deviceStatus.values().stream().max(Integer::compare).orElse(0);
        
        // 绘制条形图
        int x = 0;
        int y = chartHeight - 30; // 底部留出空间用于显示标签
        for (Map.Entry<String, Integer> entry : deviceStatus.entrySet()) {
            int barHeight = (int) ((double) entry.getValue() / maxStatusValue * (chartHeight - 50)); // 按最大值缩放
            
            // 绘制条形
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x + 10, y - barHeight, barWidth - 20, barHeight);

            // 绘制条形的标签
            g2d.setColor(Color.BLACK);
            g2d.drawString(entry.getKey(), x + barWidth / 4, y + 10);
            
            x += barWidth;
        }
    }
}