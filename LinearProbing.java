public class LinearProbing {
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
    public LinearProbing(int capacity) {
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

    // Insert value into the hash table using linear probing
    public void insert(Object object) {
        int hash = hashFunction(object, getCapacity());

        // Create initial hash to check if hash table is full
        int initialHash = hash;

        // Check if the hash table at the index is null or deleted, if not continue with the while loop
        while (getHashTable()[hash] != null && !(getHashTable()[hash].equals(getDeletedValue()))) {

            // Solve collision using linear probing
            hash++;

            // Wrap around to the beginning of the hash table if the end is reached
            hash = hash % getCapacity();

            // Check if the program has looped back to the initial hash, if so the hash table is full
            if (hash == initialHash) {
                System.out.println("Hash table is full, unable to insert " + object + " at index " + hash);
                System.out.println("Resizing hash table..." + "\n");

                // Resize the hash table to fit the new object
                resize();
                System.out.println("Resized hash table capacity: " + getCapacity() + "\n");
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

        // If the number is not divisible by any odd number, it is a prime number
        return true;
    }

    // Get the next prime number
    public int getNextPrimeNumber(int number) {
        while (!isPrimeNumber(number)) {
            number++;
        }
        return number;
    }

    //Resize the hash table if its full
    public void resize(){
        // Set new capacity to the next prime number after doubling the capacity
        int newCapacity = getNextPrimeNumber(getCapacity() * 2);
        Object[] newHashTable = new Object[newCapacity];
        Object[] oldHashTable = getHashTable();
        setCapacity(newCapacity);
        setHashTable(newHashTable);
        // Rehash all the values in the old hash table into the new hash table
        for (Object obj: oldHashTable){
            if (obj != null && !(obj.equals(getDeletedValue()))) {
                insert(obj);
            }
        }
    }
    

    // Search for value in the hash table
    public Object search(Object object) {
        int hash = hashFunction(object, getCapacity());
        int initialHash = hash;

        // Check if the hash table at the index is null, if not continue with the while loop
        while (getHashTable()[hash] != null) {
            // Check if the value at the index is the value being searched for
            if (getHashTable()[hash].equals(object)) {
                System.out.println("\nFound " + object + " at index " + hash);
                return hash;
            }
            hash++;
            hash = hash % getCapacity();
            
            // Check if the program has looped back to the initial hash
            if (hash == initialHash) {
                break;
            }
        }

        // If the value is not found, return deleted value (-1)
        System.out.println("\n" + object + " not found in the hash table.");
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
            System.out.println("Hence " + object + " cannot be deleted.");
        }
    }

    // Display the hash table
    public void display() {
        System.out.println("\n------------------------------------");
        System.out.println("Hash Table -> Linear Probing: ");
        System.out.println("(index: value)");
        for (int i = 0; i < getCapacity(); i++) {
            System.out.print(i + ": " + getHashTable()[i] + "\n");
        }
        System.out.print("------------------------------------\n");
    }

    public static void main(String args[]){
        // Create hash table object
        LinearProbing hashTable = new LinearProbing(5);
        hashTable.insert(5);
        hashTable.display();
    }
}
