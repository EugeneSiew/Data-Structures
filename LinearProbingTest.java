import java.util.Scanner;

public class LinearProbingTest {

    // Function to set user value based on the specific data type chosen and return
    // the value for further operations in main
    public static Object checkInput(String dataType, Scanner scanner) {
        Object value = null;

        switch (dataType) {
            case "string":
                if (scanner.hasNextLine()) {
                    value = scanner.nextLine();
                    if (value.equals("-1")) {
                        System.out.println("Invalid input. Do not enter -1 or try entering a string.\n");
                        value = null;
                    }
                }
                break;

            case "integer":
                if ((scanner.hasNextInt())) {
                    value = scanner.nextInt();
                    if (value.equals(-1)) {
                        System.out.println("Invalid input. Do not enter -1 or try entering an integer.\n");
                        value = null;
                    }
                    // Consume the newline character
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input. Do not enter -1 or try entering an integer.\n");
                    // Consume the invalid input
                    scanner.nextLine();
                }
                break;

            case "double":
                if (scanner.hasNextDouble()) {
                    value = scanner.nextDouble();
                    if (value.equals(-1.0)) {
                        System.out.println("Invalid input. Do not enter -1 or try entering a double.\n");
                        value = null;
                    }
                    // Consume the newline character
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input. Enter a double. \n");
                    // Consume the invalid input
                    scanner.nextLine();
                }
                break;
                
            case "character":
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    // Check if input is a single character
                    if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                        value = input.charAt(0);
                    } else {
                        System.out.println("Invalid input. Enter a single character.\n");
                    }
                }
                break;
                
            default:
                System.out.println("Invalid data type. Please enter a String, Integer, Double, or Character.\n");
                break;
        }

        // Return the value
        return value;
    }

    public static void main(String[] args) {
        // Initialise Variables
        String dataType;
        int capacity;
        boolean isValid = false;
        boolean end = false;
        Object value;
        Scanner input = new Scanner(System.in);
        LinearProbing lpObject = null;

        System.out.println("--------------Welcome to Hashing!--------------");
        System.out.println("Collision Handling Method: Linear Probing");
        System.out.println("(Note that in our implementation, the deleted value of a cell is set to -1)");
        System.out.println("-----------------------------------------------");
        System.out.println();

        // Loop to get valid capacity
        do {
            try {
                System.out.print("\nEnter hash table capacity: ");
                capacity = input.nextInt();
                // Consume the newline character
                input.nextLine();

                // Check if capacity is more than 0
                if (capacity > 0) {

                    // Create new hash table object
                    lpObject = new LinearProbing(capacity);
                    isValid = true;

                }
            } catch (Exception e) {

                System.out.println("Invalid input for capacity. Enter positive integer only.");
                input.nextLine();

            }
        } while (!isValid);

        System.out.println("Choose data type of input values.");
        System.out.println("> String \n> Integer \n> Double \n> Character");

        // Reset isValid to false to reuse it
        isValid = false;

        // Loop to get valid data type
        do {
            System.out.print("\nEnter data type name: ");
            dataType = input.nextLine().toLowerCase();

            // Check if data type is valid
            if (dataType.equals("string") || dataType.equals("integer")
                    || dataType.equals("double") || dataType.equals("character")) {

                System.out.println("You chose " + dataType + ".");
                isValid = true;

            } else {
                
                System.out.println(
                        "Invalid selection of data type. Please enter String, Integer, Double or Character only.");
            }
        } while (!isValid);

        // Loop to get valid hash table operations
        while (!end) {
            System.out.println("\nHash Table Operations: \n(1) Insert \n(2) Search \n(3) Delete \n(4) Display \n(5) Exit");
            System.out.println("\nSelect operation by entering the corresponding number: ");
            String operation = input.nextLine();

            switch (operation) {
                // Insert
                case "1":
                    // Get value to insert
                    do {
                        System.out.println("Enter value to insert: ");
                        // Check if input is the data type entered before
                        value = checkInput(dataType, input);
                    } while (value == null);

                    lpObject.insert(value);
                    lpObject.display();
                    break;

                // Search
                case "2":
                    // Get value to search
                    do {
                        System.out.println("Enter value to search: ");
                        // Check if input is the data type entered before
                        value = checkInput(dataType, input);
                    } while (value == null);

                    lpObject.search(value);
                    lpObject.display();
                    break;

                // Delete
                case "3":
                    // Get value to search
                    do {
                        System.out.println("Enter value to delete: ");
                        // Check if input is the data type entered before
                        value = checkInput(dataType, input);
                    } while (value == null);

                    lpObject.delete(value);
                    lpObject.display();
                    break;

                // Display
                case "4":
                    // Display hash table
                    System.out.println("Displaying hash table: ");
                    lpObject.display();
                    break;

                // Exit
                case "5":
                    // Exit program
                    System.out.println("Exiting program. Thank you!");
                    end = true;
                    break;

                default:
                    System.out.println("Invalid selection of operation. Please enter 1, 2, 3, 4 or 5 only.");
            }
        }
    }
}