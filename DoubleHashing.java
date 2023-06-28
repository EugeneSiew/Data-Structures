public class DoubleHashing {
    // Initialising variables
    private int capacity;
    private Object[] hashTable;
    private final int DELETEDVALUE = -1;

    // Getters and setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setHashTable(Object[] hashTable) {
        this.hashTable = hashTable;
    }

    public Object[] getHashTable() {
        return hashTable;
    }

    public int getDeletedValue() {
        return DELETEDVALUE;
    }

    // Create table when creating the hash table object
    public DoubleHashing(int capacity) {
        this.capacity = capacity;
        this.hashTable = new Object[capacity];
    }

    // Determine which data type is being inputted
    public String getType(Object obj) {

        if (obj instanceof String) {
            return "String";
        } else if (obj instanceof Integer) {
            return "Integer";
        } else if (obj instanceof Double) {
            return "Double";
        } else if (obj instanceof Character) {
            return "Character";
        } else {
            return null;
        }

    }

    // Hash function to determine index
    public int hashFunction(Object object, int capacity) {

        int hash;

        // Determine which data type is being inputted
        switch (getType(object)) {
            case "String":
                hash = object.hashCode() % capacity;
                break;
            case "Integer":
                hash = ((Integer) object) % capacity;
                break;
            case "Double":
                hash = ((Double) object).hashCode() % capacity;
                break;
            case "Character":
                hash = ((Character) object).hashCode() % capacity;
                break;
            default:
                // Throw exception if data type entered is not in the above list
                throw new IllegalArgumentException("Invalid data type. Please enter a String, Integer, Double, or Character");
        }

        // Make sure hash is positive
        return Math.abs(hash);
    }

    // Find the constant to be used in second hash function
    public int findConstant(int capacity) {
        int constant = capacity / 2;
        // Use a constant that is prime and less than the array size
        return getNextPrimeNumber(constant);
    }

    // Second hash function to determine the step size
    public int hashFunction2(Object object, int capacity) {

        int hash2;
        int constant = findConstant(capacity);
        // hash2 = constant - (object % constant), where constant is a prime number less than array size

        // Determine which data type is being inputted
        switch (getType(object)) {
            case "String":
                hash2 = constant - (object.hashCode() % constant);
                break;
            case "Integer":
                hash2 = constant - (((Integer) object) % constant);
                break;
            case "Double":
                hash2 = constant - (((Double) object).hashCode() % constant);
                break;
            case "Character":
                hash2 = constant - (((Character) object).hashCode() % constant);
                break;
            default:
                // Throw exception if data type entered is not in the above list
                throw new IllegalArgumentException("Invalid data type. Please enter a String, Integer, Double, or Character");
        }

        // Make sure hash is positive
        return Math.abs(hash2);
    }

    // Insert value into the hash table using linear probing
    public void insert(Object object) {
        // Find first hash and second hash
        int hash = hashFunction(object, getCapacity());
        int stepSize = hashFunction2(object, getCapacity());
        System.out.println("Inserting " + object);
        System.out.println("First hash value: " + hash);
        System.out.println("Second hash value: " + stepSize);
        System.out.println();
        
        // Create initial hash to check if hash table is full        
        int initialHash = hash;

        // Check if the hash table at the index is null or deleted, if not continue with the while loop       
        while (getHashTable()[hash] != null && !(getHashTable()[hash].equals(getDeletedValue()))) {

            // Solve collision using double hashing
            hash += stepSize;

            // Wrap around to the beginning of the hash table if the end is reached
            hash = hash % getCapacity();

            // Check if the program has looped back to the initial hash, if so the hash table is full
            if (hash == initialHash) {
                System.out.println("Hash table is full, unable to insert " + object + " at index " + hash);
                System.out.println("Resizing hash table..." + "\n");
                
                // Resize the hash table to fit the new object
                resize();
                insert(object);
                return;
            }
        }

        // Insert object into the hash table
        getHashTable()[hash] = object;
    }

    // Detect prime number
    public boolean isPrimeNumber(int number) {

        // Eliminate the need to check versus even numbers
        if (number % 2 == 0){
            return false;
        }

        // Check against all odd numbers
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        // If the number is not divisible by any odd number >=3, it is a prime number
        return true;
    }

    // Get the next prime number
    public int getNextPrimeNumber(int number) {
        while (!isPrimeNumber(number)) {
            number++;
        }
        return number;
    }

    // Resize the hash table if its full
    public void resize() {
        // Set new capacity to the next prime number after doubling the capacity
        int newCapacity = getNextPrimeNumber(getCapacity() * 2);
        Object[] newHashTable = new Object[newCapacity];
        Object[] oldHashTable = getHashTable();
        setCapacity(newCapacity);
        setHashTable(newHashTable);
        // Rehash all the values in the old hash table into the new hash table
        for (Object obj : oldHashTable) {
            insert(obj);
        }
    }

    // Search for value in the hash table
    public Object search(Object object) {
        // Find first hash and second hash
        int hash = hashFunction(object, getCapacity());
        int stepSize = hashFunction2(object, getCapacity());
        int initialHash = hash;

        // Check if the hash table at the index is null
        while (getHashTable()[hash] != null) {
            // Check if the value at the index is the value being searched for
            if (getHashTable()[hash].equals(object)) {
                System.out.println("Found " + object + " at index " + hash);
                return hash;
            }
            hash += stepSize;
            hash = hash % getCapacity();

            // Check if the program has looped back to the initial hash
            if (hash == initialHash) {
                break;
            }
        }

        // If the value is not found, return deleted value (-1)
        System.out.println(object + " not found in the hash table");
        return getDeletedValue();
    }

    // Delete value from the hash table
    public void delete(Object object) {
        // Get the index of the value being searched for using the search function
        int hash = (int) search(object);

        // Check if the value is the deleted value (-1)
        // If not delete the value by replacing the value with the deleted value
        if (hash != getDeletedValue()) {
            getHashTable()[hash] = getDeletedValue();
            System.out.println("Deleted '" + object + "' at index " + hash);
        } 
        else {
            System.out.println("'" + object + "' not found in the hash table. Hence cannot be deleted");
        }
    }

    // Display the hash table
    public void display() {
        System.out.println("Hash Table -> Double Hashing: ");
        System.out.println("(index: value)" + "\n");
        for (int i = 0; i < getCapacity(); i++) {
            System.out.print(i + ": " + getHashTable()[i] + "\n");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        DoubleHashing test = new DoubleHashing(11);

        test.insert(1);
        test.insert(4);
        test.insert(6);
        test.insert(4);
        test.insert(4);
        test.insert(4);
        test.display();

        test.delete(4);
        test.display();
        
        test.delete(4);
        test.display();

        test.delete(4);
        test.display();

        test.delete(4);
        test.display();
    }
}
