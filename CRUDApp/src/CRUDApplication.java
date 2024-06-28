import java.util.Scanner;

public class CRUDApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String tableName = null;
        String primaryKeyColumn = null;
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("Choose an option:");
            System.out.println("1. Create a table");
            System.out.println("2. Select all records");
            System.out.println("3. Insert a new record");
            System.out.println("4. Update an existing record");
            System.out.println("5. Delete a record");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    tableName = CreateTable.createTable();
                    if (tableName != null) {
                        System.out.print("Enter primary key column name: ");
                        primaryKeyColumn = scanner.nextLine();
                    }
                    break;
                case 2:
                    if (tableName != null) {
                        Select.selectAll(tableName);
                    } else {
                        System.out.println("No table created yet.");
                    }
                    break;
                case 3:


                    if (tableName != null) {
                        Insert.insertRecord(tableName);
                    } else {
                        System.out.println("No table created yet.");
                    }
                    break;
                case 4:


                    if (tableName != null) {
                        System.out.print("Enter record ID to update: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over
                        Update.updateRecord(tableName, primaryKeyColumn, id);
                    } else {
                        System.out.println("No table created yet.");
                    }
                    break;
                case 5:
                    if (tableName != null) {
                        System.out.print("Enter record ID to delete: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over
                        Delete.deleteRecord(tableName, primaryKeyColumn, id);
                    } else {
                        System.out.println("No table created yet.");
                    }
                    break;
                case 6:
                    continueLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        System.out.println("Exiting CRUD application. Bye!");
        scanner.close();
    }
}
