import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateTable {
    public static String createTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine();

        System.out.print("Enter number of columns: ");
        int numColumns = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");

        for (int i = 0; i < numColumns; i++) {
            System.out.print("Enter column name: ");
            String columnName = scanner.nextLine();

            System.out.print("Enter column type (e.g., INT, VARCHAR(255)): ");
            String columnType = scanner.nextLine();

            createTableSQL.append(columnName).append(" ").append(columnType);
            if (i < numColumns - 1) {
                createTableSQL.append(", ");
            }
        }

        System.out.print("Enter primary key column name: ");
        String primaryKeyColumn = scanner.nextLine();

        createTableSQL.append(", PRIMARY KEY (" + primaryKeyColumn + "))");

        try (Connection connection = JDBCUtil.getJDBCConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL.toString());
            System.out.println("Table '" + tableName + "' created successfully with primary key.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Error creating table: " + e.getMessage());
        }

        return tableName;
    }
}
