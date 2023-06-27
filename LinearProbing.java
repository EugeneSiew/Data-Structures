public class LinearProbing {
    private int capacity;
    private Object[] hashTable;
    private final int DELETEDVALUE = -1;	

    public int getCapacity() {
        return capacity;
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

    public String getType(Object obj) {
    if (obj instanceof String) {
        return "String";
    } else if (obj instanceof Integer) {
        return "Integer";
    } else if (obj instanceof Double) {
        return "Double";
    } else {
        throw new IllegalArgumentException("Unsupported data type");
    }
    }

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
            default:
                throw new IllegalArgumentException("Unsupported data type");
        }
        
        // Make sure hash is positive
        return Math.abs(hash);
    }

    // Insert value into the hash table using linear probing
    public void insert(Object object) {
        int hash = hashFunction(object, getCapacity());
        int initialHash = hash;
        while (getHashTable()[hash] != null) {
            hash++;
            hash = hash % capacity;

            if (hash == initialHash) {
                System.out.println("Hash table is full, unable to insert " + object + " at index " + hash);
                return;
            }
        }
        getHashTable()[hash] = object;
    }


    // Search for value in the hash table
    public Object search(Object object) {
        int hash = hashFunction(object, getCapacity());
        int initialHash = hash;
        while (getHashTable()[hash] != null) {
            if (getHashTable()[hash].equals(object)) {
                System.out.println("Found " + object + " at index " + hash);
                return hash;
            }
            hash++;
            hash = hash % capacity;

            if (hash == initialHash) {
                break;
            }
        }
    
        System.out.println(object + " not found in the hash table");
        return getDeletedValue();
    }

    // Delete value from the hash table
    public void delete(Object object) {
        int hash = (int) search(object);
        if (hash != getDeletedValue()) {
            getHashTable()[hash] = getDeletedValue();
            System.out.println("Deleted '" + object + "' at index " + hash);
        }
        else {
            System.out.println("'" + object + "' not found in the hash table. Hence cannot be deleted");
        }
        /*
        int hash = hashFunction(object, getCapacity());
        int initialHash = hash;
        while (getHashTable()[hash] != null) {
            if (getHashTable()[hash].equals(object)) {
                getHashTable()[hash] = null;
                System.out.println("Deleted '" + object + "' at index " + hash);
                return;
            }
            hash++;
            hash = hash % capacity;

            if (hash == initialHash) {
                break;
            }
        }
    
        System.out.println(object + " not found in the hash table"); 
        */
    }

    public void display() {
        for (int i = 0; i < getHashTable().length; i++) {
            System.out.print(i + ": " + getHashTable()[i] + "\n");
        }
    }

    public static void main(String[] args) {
        LinearProbing test = new LinearProbing(11);
        test.insert("cats");
        test.insert("Heel");
        test.insert("was");
        test.insert("Duck");
        test.insert("Calf");
        test.insert("Bag");
        test.insert("Hut");
        test.insert("Tag");
        test.search("Heel");
        /*
        test.insert(5);
        test.search("cats");
        test.search(5);
        test.insert("Duck");
        test.insert("Calf");
        test.insert("Bag");
        test.insert("Hiut");
        test.search("him");
        */
        test.display();
        test.delete("hiu");
        test.display();
        
    }
}