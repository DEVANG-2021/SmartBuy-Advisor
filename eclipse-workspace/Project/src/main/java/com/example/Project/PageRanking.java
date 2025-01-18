package com.example.Project;

import java.io.BufferedReader;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Devang's page ranking task - 5 of assignment - 2

// Class representing an AVL tree to store keyword frequencies
class AVLTree {
    // Node class representing each node in the AVL tree
    class Node {
        String keyword; // The keyword stored in this node elements
        int frequency; // Frequency of the keyword (from node elements)
        Node left, right; // Left and right children nodes (From node ELEMENT)
        int height; // Height of the node for balancing purposes (which is indepth of any tree)

        // Constructor to initialize the node
        Node(String keyword, int frequency) {
            this.keyword = keyword; // this  points to the current object with original keyword value parameter
            this.frequency = frequency; // this  points to the current object with frequency parameter
            this.height = 1; // New node element is initially added at leaf node of the tree
        }
    }

    private Node root; // Root node element of the AVL tree (main element)

    // Method to insert a keyword into the AVL tree
    public void insert(String keyword) {
        root = insert(root, keyword);   // insert method to adding root node with keyword
    }

    // Recursive method to insert a keyword and balance the tree
    private Node insert(Node node, String keyword) {
        if (node == null) {
            return new Node(keyword, 1); // Create a new node element in tree if position is found
        }

        // If keyword is found, increment its frequency
        if (keyword.equals(node.keyword)) {
            node.frequency++; // here we increment the frequency of node element
            return node;  // returning the node
        }
        // If keyword is smaller, go left; otherwise, go right
        if (keyword.compareTo(node.keyword) < 0) {  // checking the condition - comparing the keyword
            node.left = insert(node.left, keyword);   // insert in left
        } else {
            node.right = insert(node.right, keyword);  // adding in right node
        }

        // Update the height and balance the tree
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));  // calculating using math function and use getheight function of tree
        return balance(node);  // now here we are balancing the node element
    }

    // Balance the tree to maintain the AVL property
    private Node balance(Node node) {      // balancing the avl tree - function which takes the node element with class Node
        int balance = getBalance(node); // Get the balance factor

        // Left Left Case
        if (balance > 1) {    // checking the condition > 1
            if (getBalance(node.left) < 0) {       // get balance and taking the node left element by less than 0 (comparing condition)
                node.left = rotateLeft(node.left); // Left Right Case   // here we are rotating the cas in left
            }
            return rotateRight(node); // Left Left Case  - here is the left left case and rotating right
        }

        // Right Right Case
        if (balance < -1) {    // balacnce < -1 condition
            if (getBalance(node.right) > 0) {     // getbalance and node right > 1 conditoon initiated
                node.right = rotateRight(node.right); // Right Left Case  -  rotating right node
            }
            return rotateLeft(node); // Right Right Case  --  different cases with the right node and right ndoe element
        }
        return node; // Node is balanced
    }

    // Left rotation
    private Node rotateLeft(Node node) {
        Node newRoot = node.right; // Right child becomes new root  (generate new element )
        node.right = newRoot.left; // Left subtree of new root becomes right subtree of old root
        newRoot.left = node; // Old root becomes left child of new root    (old element becomeing the main element which is root node element )
        // Update heights
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));  // calculating with math maximum function and get height of left node
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right)); // c get height of right node
        return newRoot; // Return new root
    }

    // Right rotation
    private Node rotateRight(Node node) {
        Node newRoot = node.left; // Left child becomes new root   --- new becoming the main (root element )
        node.left = newRoot.right; // Right subtree of new root becomes left subtree of old root   --- right subtree and left subtree
        newRoot.right = node; // Old root becomes right child of new root
        // Update heights
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));    // again calculating the function with math max function and get height in right side argument
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right)); // again calculating the function with math max function and get height in left and right side argument
        return newRoot; // Return new root   -- returning the result which is new main element - root node
    }

    // Get height of a node
    private int getHeight(Node node) {
        return node == null ? 0 : node.height;   // get height of node
    }

    // Get balance factor of a node
    private int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);   // substraction perfomed on node
    }

    // Get frequency of a keyword
    public int getFrequency(String keyword) {
        return getFrequency(root, keyword);   // get frequency
    }

    // Recursive method to get frequency
    private int getFrequency(Node node, String keyword) {
        if (node == null) {
            return 0; // Keyword not found
        }
        // Return frequency if keyword is found
        if (keyword.equals(node.keyword)) {    // comparing the keyword using equals method
            return node.frequency;
        } else if (keyword.compareTo(node.keyword) < 0) {  // again comaprision with less then 0 condition
            return getFrequency(node.left, keyword); // Search left
        } else {
            return getFrequency(node.right, keyword); // Search right
        }
    }
}

// Class representing a max heap to store ranked products
class MaxHeap {
    private final List<Product> heap = new ArrayList<>(); // List to store wearable smartwatches products

    // Insert a product into the max heap
    public void insert(Product product) {  // product object with insert class
        heap.add(product); // Add product to the end of the list
        int index = heap.size() - 1; // Get the index of the newly added product

        // Bubble up the new product to maintain max heap property
        while (index > 0) {
            int parentIndex = (index - 1) / 2; // Get the parent's index
            // If the current product's frequency is greater than the parent's, swap them
            if (heap.get(index).frequency <= heap.get(parentIndex).frequency) {
                break;
            }
            Collections.swap(heap, index, parentIndex); // Swap with parent
            index = parentIndex; // Move up the heap
        }
    }

    // Extract the top k products from the max heap
    public List<Product> extractTop(int k) {
        List<Product> topProducts = new ArrayList<>();
        // Collect top k products from the heap
        for (int i = 0; i < Math.min(k, heap.size()); i++) {   // for loop which is very important for heap
            topProducts.add(heap.get(i));  // getting heap element
        }
        return topProducts; // Return the list of top products
    }
}

// Main class for page ranking system
class PageRanking {

    // Read product data from a CSV file
    public static List<Product> readProductData(String filePath) {
        List<Product> productList = new ArrayList<>(); // List to store products
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line of the file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split by comma
                // Create a product object if there are enough values
                if (values.length >= 8 && !values[0].contains("Name")) {  // different values wth different parameters
                    productList.add(new Product(
                    		 values[0], // productName
                             values[1], // productPrice
                             values[2],  // productGps
                             values[3], // productConnectivity
                             values[4], // productHeartRateMonitor
                             values[5], // productOperatingSystem
                             values[6], // productTouchScreen
                             values[7], // productBatteryLife
                             values[8], // productBuiltInStorage
                             values[9], // productImageUrl
                             0 // initialize keywordMatchFrequency to 0
                    ));
                }
            }
        } catch (IOException e) {  // IO exception performed
            e.printStackTrace(); // Print stack trace for any IO exceptions
        }
        return productList; // Return the list of products
    }

    // Boyer-Moore search algorithm to count keyword occurrences in text
    public static int boyerMoore(String text, String pattern) {
        Map<Character, Integer> badChar = preprocessPattern(pattern); // Preprocess the pattern
        int m = pattern.length(); // Length of the pattern
        int n = text.length(); // Length of the text
        int s = 0; // Shift of the pattern with respect to text
        int count = 0; // Count of occurrences

        // Search for pattern in text
        while (s <= (n - m)) {
            int j = m - 1; // Start comparing from the end of the pattern

            // Check for a match
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j))
                j--;

            // If a match is found
            if (j < 0) {
                count++; // Increment count
                // Shift the pattern based on bad character heuristic
                s += (s + m < n) ? m - badChar.getOrDefault(text.charAt(s + m), -1) : 1;
            } else {
                // Shift based on the bad character heuristic
                s += Math.max(1, j - badChar.getOrDefault(text.charAt(s + j), -1));
            }
        }
        return count; // Return the count of occurrences
    }

    // Preprocess the pattern to create a bad character table
    public static Map<Character, Integer> preprocessPattern(String pattern) {
        Map<Character, Integer> badChar = new HashMap<>(); // Table for bad characters
        int m = pattern.length(); // Length of the pattern

        // Fill the bad character table
        for (int i = 0; i < m; i++) {
            badChar.put(pattern.charAt(i), i); // Map character to its index
        }
        return badChar; // Return the bad character table
    }

    // Calculate the frequency of keywords in product names
    public static void calculateKeywordFrequency(List<Product> productList, List<String> searchKeywords) {
        for (Product product : productList) {
            // Create a new AVL tree for keyword frequencies
            AVLTree keywordTree = new AVLTree();
            int totalFrequency = 0; // Total frequency of matches for the product
            for (String keyword : searchKeywords) {
                // Count occurrences of each keyword in the product name
                totalFrequency += boyerMoore(product.name.toLowerCase(), keyword.toLowerCase());
                keywordTree.insert(keyword.toLowerCase()); // Insert keyword into AVL tree
            }
            product.frequency = totalFrequency; // Set the product frequency
        }
    }

    // Rank the products based on their frequency
    public static List<Product> rankPages(List<Product> productList) {
        // Sort products based on frequency in descending order
        productList.sort((p1, p2) -> Integer.compare(p2.frequency, p1.frequency));
        return productList; // Return ranked product list
    }

    // Search for products based on a query
    public static List<Product> searchProducts(String filePath, String query) {
        List<Product> productList = readProductData(filePath); // Read product data from CSV
        List<String> searchKeywords = Arrays.asList(query.toLowerCase().split("\\s+")); // Split query into keywords
        calculateKeywordFrequency(productList, searchKeywords); // Calculate frequencies
        return rankPages(productList); // Rank and return products
    }

    // Display the top ranked products
    public static void displayTopRankedProducts(List<Product> rankedProducts) {
        MaxHeap maxHeap = new MaxHeap(); // Create a max heap for top products
        for (Product product : rankedProducts) {
            maxHeap.insert(product); // Insert each product into the heap
        }
        List<Product> topProducts = maxHeap.extractTop(10); // Extract top 10 products
        System.out.println("Top Ranked Products:");
        // Display details of top products
        for (Product product : topProducts) {
            System.out.println("Product: " + product.name +
                    ", Price: " + product.price +
                    ", GPS: " + product.gps +
                    ", Connectivity: " + product.connectivity +
                    ", Heart Rate Monitor: " + product.heartRateMonitor +
                    ", Operating System: " + product.operatingSystem +
                    ", Touch Screen: " + product.touchScreen +
                    ", Battery Life: " + product.batteryLife +
                    ", Built-In Storage: " + product.builtInStorage +
                    ", Image URL: " + product.imageUrl +
                    ", Frequency: " + product.frequency);
        }
    }

    // Main method for running the application
    public static void main(String[] args) {
        String csvFilePath = "products.csv"; // Path to the CSV file containing product data
        Scanner scanner = new Scanner(System.in); // Create a scanner for user input
        String searchQuery; // Variable to store search queries
        List<Product> rankedProducts; // List to store ranked products

        // Loop for searching products
        while (true) {
            System.out.print("Enter Search Query (or type 'exit' to quit): ");
            searchQuery = scanner.nextLine(); // Read user input

            if (searchQuery.equalsIgnoreCase("exit")) {
                break; // Exit if the user types 'exit'
            }

            // Search and rank products based on the query
            rankedProducts = searchProducts(csvFilePath, searchQuery);
            displayTopRankedProducts(rankedProducts); // Display top ranked products
            System.out.println();
        }
        scanner.close(); // Close the scanner
    }
}
//@JsonIgnoreProperties(ignoreUnknown = true)
class Product {
	 @JsonProperty("name")
	    String name;

	    @JsonProperty("price")
	    String price;

	    @JsonProperty("imageUrl")
	     String imageUrl;

	    @JsonProperty("operatingSystem")
	     String operatingSystem;

	    @JsonProperty("connectivity")
	    String connectivity;
	    
	    @JsonProperty("touchScreen")
	    String touchScreen;
	    
	    @JsonProperty("builtInStorage")
	    String builtInStorage;

	    @JsonProperty("gps")
	    String gps;

	    @JsonProperty("heartRateMonitor")
	    String heartRateMonitor;

	    @JsonProperty("batteryLife")
	    String batteryLife;

	    @JsonProperty("frequency")
	    int frequency;

	    
//    String name; // Name of the product
//    String price; // Price of the product
//    String imageUrl; // Image URL of the product
//    String operatingSystem; // Operating system (if applicable)
//    String connectivity; // Connectivity options (e.g., Bluetooth, Wi-Fi)
//    String gps; // GPS capability
//    String heartRateMonitor; // Heart rate monitor availability
//    String batteryLife; // Battery life description
//    int frequency; // Frequency of keyword matches

    // Constructor to initialize the product details with different parameters
    public Product(String name, String price,  String gps,String connectivity, String heartRateMonitor, String operatingSystem,
    		       String touchScreen,String batteryLife, String builtInStorage, String imageUrl, int frequency) {
    	  this.name = name;
          this.price = price;
          this.gps = gps;
          this.connectivity = connectivity;
          this.heartRateMonitor = heartRateMonitor;
          this.operatingSystem = operatingSystem;
          this.touchScreen = touchScreen;
          this.batteryLife = batteryLife;
          this.builtInStorage = builtInStorage;
          this.imageUrl = imageUrl;
          this.frequency = frequency;
    }

}

