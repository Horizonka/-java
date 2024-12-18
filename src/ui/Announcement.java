package src.ui;

import java.util.Date;

public class Announcement {
    private  final String title;
    private  final String content;
    private  final Date date;

    public Announcement(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    // Getter and Setter methods
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}

