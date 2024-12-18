package src.ui;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AnnouncementManager {
    private static final String URL = "jdbc:sqlite:library.db";

    // public void addAnnouncement(Announcement announcement) {
    //     String query = "INSERT INTO announcements (title, content, date) VALUES (?, ?, ?)";
    //     try (Connection conn = DriverManager.getConnection(URL);
    //          PreparedStatement pstmt = conn.prepareStatement(query)) {
    //         pstmt.setString(1, announcement.getTitle());
    //         pstmt.setString(2, announcement.getContent());
    //         pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(announcement.getDate()));
    //         pstmt.executeUpdate();
    //     } catch (SQLException e) {
    //          e.printStackTrace();
    //          logger.severe("Error adding announcement: " + e.getMessage());
            
    //     }
    // }
    private static final Logger logger = Logger.getLogger(AnnouncementManager.class.getName());
    
    public void addAnnouncement(Announcement announcement) {
        String query = "INSERT INTO announcements (title, content, date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, announcement.getTitle());
            pstmt.setString(2, announcement.getContent());
            pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(announcement.getDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Log the exception with a message
            logger.log(Level.SEVERE, "Error adding announcement: ", e);
        }
    }

    public List<Announcement> getAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        String query = "SELECT * FROM announcements ORDER BY date DESC";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date"));
                announcements.add(new Announcement(title, content, date));
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.log(Level.SEVERE, "Error getting announcement: ", e);
        }
        return announcements;
    }
}
