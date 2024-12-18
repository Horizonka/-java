import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSQLiteConnection {
     private static final Logger logger = Logger.getLogger(TestSQLiteConnection.class.getName());
    public static void main(String[] args) {
        
        String url = "jdbc:sqlite:library.db"; // SQLite数据库文件路径

        try {
            Class.forName("org.sqlite.JDBC"); // 加载SQLite JDBC驱动
            // 使用try-with-resources语法
            try (Connection conn = DriverManager.getConnection(url)) {
                System.out.println("连接成功！");
            } // Connection会在这里自动关闭
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error adding announcement: ", e);
        }
    }
} 



