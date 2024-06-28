import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    public static void deleteRecord(String tableName, String primaryKeyColumn, int id) {
        String deleteSQL = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";

        try (Connection connection = JDBCUtil.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Record deleted successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
