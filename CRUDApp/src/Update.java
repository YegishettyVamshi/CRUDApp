import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Update {
    public static void updateRecord(String tableName, String primaryKeyColumn,int id) {
        String columnQuery = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";

        try (Connection connection = JDBCUtil.getJDBCConnection();
             PreparedStatement columnStatement = connection.prepareStatement(columnQuery)) {

            columnStatement.setString(1, tableName);
            ResultSet rs = columnStatement.executeQuery();

            List<String> columnNames = new ArrayList<>();
            List<String> columnTypes = new ArrayList<>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                if (!columnName.equalsIgnoreCase(primaryKeyColumn)) {
                    columnNames.add(columnName);
                    columnTypes.add(dataType);
                }
            }

            StringBuilder updateSQL = new StringBuilder("UPDATE " + tableName + " SET ");
            for (int i = 0; i < columnNames.size(); i++) {
                updateSQL.append(columnNames.get(i)).append(" = ?");
                if (i < columnNames.size() - 1) {
                    updateSQL.append(", ");
                }
            }
            updateSQL.append(" WHERE ").append(primaryKeyColumn).append(" = ?");

            try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL.toString())) {
                Scanner scanner = new Scanner(System.in);
                for (int i = 0; i < columnNames.size(); i++) {
                    System.out.print("Enter value for " + columnNames.get(i) + " (" + columnTypes.get(i) + "): ");
                    String value = scanner.nextLine();
                    setPreparedStatementValue(updateStatement, i + 1, columnTypes.get(i), value);
                }
                updateStatement.setInt(columnNames.size() + 1, id);

                updateStatement.executeUpdate();
                System.out.println("Record updated successfully.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void setPreparedStatementValue(PreparedStatement ps, int parameterIndex, String dataType, String value) throws SQLException {
        switch (dataType.toLowerCase()) {
            case "int":
            case "integer":
                ps.setInt(parameterIndex, Integer.parseInt(value));
                break;
            case "varchar":
            case "char":
                ps.setString(parameterIndex, value);
                break;
            // Add cases for other data types as needed
            default:
                ps.setString(parameterIndex, value);
                break;
        }
    }
}
