import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Insert {
    public static void insertRecord(String tableName) {
        String columnQuery = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND TABLE_SCHEMA = DATABASE()";

        try (Connection connection = JDBCUtil.getJDBCConnection();
             PreparedStatement columnStatement = connection.prepareStatement(columnQuery)) {

            columnStatement.setString(1, tableName);
            ResultSet rs = columnStatement.executeQuery();

            List<String> columnNames = new ArrayList<>();
            List<String> columnTypes = new ArrayList<>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                columnNames.add(columnName);
                columnTypes.add(dataType);
            }

            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " (");
            for (int i = 0; i < columnNames.size(); i++) {
                insertSQL.append(columnNames.get(i));
                if (i < columnNames.size() - 1) {
                    insertSQL.append(", ");
                }
            }
            insertSQL.append(") VALUES (");
            for (int i = 0; i < columnNames.size(); i++) {
                insertSQL.append("?");
                if (i < columnNames.size() - 1) {
                    insertSQL.append(", ");
                }
            }
            insertSQL.append(")");

            try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL.toString())) {
                Scanner scanner = new Scanner(System.in);
                for (int i = 0; i < columnNames.size(); i++) {
                    System.out.print("Enter value for " + columnNames.get(i) + " (" + columnTypes.get(i) + "): ");
                    String value = scanner.nextLine();
                    setPreparedStatementValue(insertStatement, i + 1, columnTypes.get(i), value);
                }

                insertStatement.executeUpdate();
                System.out.println("Record inserted successfully.");
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
            case "text":
                ps.setString(parameterIndex, value);
                break;
            case "float":
                ps.setFloat(parameterIndex, Float.parseFloat(value));
                break;
            case "double":
                ps.setDouble(parameterIndex, Double.parseDouble(value));
                break;
            // Add cases for other data types as needed
            default:
                ps.setString(parameterIndex, value);
                break;
        }
    }
}
