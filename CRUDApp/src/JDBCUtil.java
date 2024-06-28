import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    private JDBCUtil() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getJDBCConnection() throws SQLException, IOException {
        FileInputStream fis = new FileInputStream("/Users/vamshikrishna/eclipse-workspace/Hibernate/CRUDApp/src/application.properties");
        Properties properties = new Properties();
        properties.load(fis);
        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));

        return connection;
    }

    public static void cleanUp(Connection connection, java.sql.Statement statement, java.sql.ResultSet resultSet) throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
    }
}
