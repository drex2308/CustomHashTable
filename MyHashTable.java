/**
 * HashTable Implementation with linear probing,
 * implementing an hash table interface 'MyHTInterface'.
 *
 * @author Dhanush Venkataramu
 */
public class MyHashTable implements MyHTInterface {
    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * Instance variable to count the number of collisions.
     */
    private int numberOfCollisons;
    /**
     * Instance variable to count number of words in list at any time.
     */
    private int numberOfWords;
    /**
     * Constant to store the default array size.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Constant to store the default load factor to maintain for faster operations.
     */
    private static final double MAX_LF = 0.5;
    /**
     * Constant to store the value of constant multiplier for Horner's process.
     */
    private static final int CONST_MUL = 27;
    /**
     * Constant to store the least prime number value.
     */
    private static final int LEAST_PRIME = 3;
    /**
     * Constant to store the placeholder for deleted items in hash table.
     */
    private static final DataItem DELETED = new DataItem("");
    /**
     * Default constructor with default array size 10.
     */
    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Parameterized constructor for class MyHashtable.
     * @param initCapacity specifies the user specified capacity.
     */
    public MyHashTable(int initCapacity) {
        if (initCapacity <= 0) {
            throw new RuntimeException("cannot have a zero size table");
        }
        hashArray = new DataItem[initCapacity];
        numberOfWords = 0;
        numberOfCollisons = 0;
    }
    /**
     * Implementation of the insert method from hash table interface.
     *
     * Word checked for validation. Moving from the hash of the given string
     * to the end of that cluster, checking for first available delete position,
     * any collisions and to increase frequency if key already present.
     */
    @Override
    public void insert(String value) {
        // validating user provided word.
        if (!validateWord(value)) {
            return;
        }
        //Calculating hash, to find index in hashArray.
        int hash = hashValue(value);
        int myhash = hash;
        int availIndex = hash;
        boolean delFound = false;
        int counter = 0;
        boolean colFound = false;
        //loop until we find end of cluster, counter used to avoid  looping.
        while (hashArray[hash] != null && counter < hashArray.length) {
            counter++;
            // increase frequency if value already present.
            if (hashArray[hash].value.equals(value)) {
                hashArray[hash].frequency += 1;
                return;
            }
            //store the first available space marked deleted, if any.
            if (!delFound && hashArray[hash] == DELETED) {
                delFound = true;
                availIndex = hash;
            }
            //check if there's any collision, flag true if found.
            if (hashArray[hash] != DELETED && myhash == hashValue(hashArray[hash].value)) {
                colFound = true;
            }
            hash = (hash + 1) % hashArray.length;
        }
        //if a deleted space found, update space index.
        if (delFound) {
            hash = availIndex;
        }
        //insert element and increase number of words.
        hashArray[hash] = new DataItem(value);
        numberOfWords++;
        //check for collision flag, increment if true.
        if (colFound) {
            numberOfCollisons += 1;
        }
        //check for load factor for rehash requirement.
        //calling a helper method.
        checkCapacity();
        }
    /**
     * Implementation for the size() method from hash table interface.
     * Returning the instance variable storing number of words.
     * Time Complexity: O(1).
     */
    @Override
    public int size() {
        return numberOfWords;
    }
    /**
     * Implementation of display() method from hash table interface.
     * Calling a helper method to display based on space state.
     */
    @Override
    public void display() {
        for (int i = 0; i < hashArray.length - 1; i++) {
             helperDisplay(hashArray[i]);
             System.out.print(" ");
        }
        helperDisplay(hashArray[hashArray.length - 1]);
        System.out.println();
    }
    /**
     * Implementation of contains() method from hash table interface.
     * Calling helper method search() to check if element present in hash table.
     */
    @Override
    public boolean contains(String key) {
        //validate user provided key.
        if (!validateWord(key)) {
            return false;
        }
        int found = search(key);
        if (found >= 0) {
            return true;
        }
        return false;
    }
    /**
     * Implementation for the numOfCollisions() method from hash table interface.
     * Returning the instance variable storing number of collisions.
     * Time Complexity: O(1).
     */
    @Override
    public int numOfCollisions() {
        return numberOfCollisons;
    }
    /**
     * Implementation of hashValue() method from hash table interface.
     * Calling helper method hashFunc() to calculate hash.
     */
    @Override
    public int hashValue(String value) {
        return hashFunc(value);
    }
    /**
     * Implementation of showFrequency() method from hash table interface.
     * calling helper method search() to get index of key, if present.
     */
    @Override
    public int showFrequency(String key) {
        //validate user provided key.
        if (!validateWord(key)) {
            return 0;
        }
        int indexHash = search(key);
        if (indexHash >= 0) {
            return hashArray[indexHash].frequency;
        }
        return 0;
    }
    /**
     * Implementation of remove() method from hash table interface.
     * calling helper method search() to get index of key, if present.
     */
    @Override
    public String remove(String key) {
        //validate user provided key.
        if (!validateWord(key)) {
            return null;
        }
        int hash = search(key);
        if (hash >= 0) {
            DataItem remItem = hashArray[hash];
            String data = remItem.value;
            hashArray[hash] = DELETED;
            remItem = null;
            numberOfWords--;
            return data;
        }
        return null;
    }
    /**
     * Helper method to check if its a valid word.
     * @param value specifies the word to be checked.
     * @return true if valid word, false otherwise.
     */
    private boolean validateWord(String value) {
        if (value == null) {
            return false;
        }
        return value.matches("^[A-Za-z]+$");
    }
    /**
     * Helper method to calculate the hash of given string, using Horner's process.
     * @param input specifies the input string.
     * @return hash calculated for the input string.
     */
    private int hashFunc(String input) {
        // converting string value to int using Horner's process, compress using modular.
        int i = 0;
        int len = input.length();
        int value = 0;
        while (i < len - 1) {
            value = ((value + (input.charAt(i) - '`')) * CONST_MUL) % hashArray.length;
            i = i + 1;
        }
        value = (value + (input.charAt(i) - '`')) % hashArray.length;
        return value;
    }
    /**
     * Helper method to check capacity at insertion.
     * call helper method rehash() if capacity reached.
     */
    private void checkCapacity() {
        double curLF = (double) size() / (double) hashArray.length;
        if (curLF > MAX_LF) {
          rehash();
        }
    }
    /**
     * Helper function to rehash as called when LF capacity reached.
     * doubles array length and rehash items whenever the load factor is reached.
     * Prints a log in console.
     */
    private void rehash() {
        DataItem[] temp = hashArray;
        hashArray = new DataItem[getPrime(hashArray.length * 2 + 1)];
        System.out.println(new StringBuilder().append("Rehashing ")
                                              .append(size())
                                              .append(" items, new length is ")
                                              .append(hashArray.length)
                                              .toString());
        numberOfCollisons = 0;
        numberOfWords = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null && temp[i] != DELETED) {
                    insert(temp[i].value);
                    int newindex = search(temp[i].value);
                    hashArray[newindex].frequency = temp[i].frequency;
            }
        }
        temp = null;
    }
    /**
     * Helper method to get the next prime number during rehashing.
     * Used for new array length.
     * @param n specifies the (double + 1) of current array length.
     * @return nearest prime number from the given n.
     */
    private int getPrime(int n) {
        if (n <= LEAST_PRIME && n > 0) {
            return n;
        }
        if (primeCheck(n)) {
            return n;
        }
        while (true) {
            n = n + 1;
            if (primeCheck(n)) {
                return n;
            }
        }
    }
    /**
     * Helper method for getPrime(), to check if a number is prime.
     * @param n specifies the number to be checked.
     * @return true if prime, false otherwise.
     */
    private boolean primeCheck(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * Helper method to display the element based on space state.
     * @param ele specifies the element of hashArray.
     */
    private void helperDisplay(DataItem ele) {
        if (ele == null) {
            System.out.print("**");
            return;
        }
        if (ele == DELETED) {
            System.out.print("#DEL#");
            return;
        }
        System.out.print(new StringBuilder().append("[")
                                            .append(ele.value)
                                            .append(", ")
                                            .append(ele.frequency)
                                            .append("]")
                                            .toString());
    }
    /**
     * Helper method to search for an index of key in hash table, if present.
     * Loops through cluster from key hash, counter kept to prevent looping.
     * @param key specifies the key value to be searched.
     * @return index of the key in hash table if found, -1 otherwise.
     */
    private int search(String key) {
        int hash = hashValue(key);
        int counter = 0;
        while (hashArray[hash] != null && counter < hashArray.length) {
            counter++;
            if (hashArray[hash].value.equals(key)) {
                return hash;
            }
            hash = (hash + 1) % hashArray.length;
        }
        return -1;
    }
    /**
     * private static data item nested class.
     */
    private static final class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;
        /**
         * Constructor for class DataItem.
         * @param key specifies the key value of new item.
         */
        private DataItem(String key) {
            value = key;
            frequency = 1;
        }
    }
}
