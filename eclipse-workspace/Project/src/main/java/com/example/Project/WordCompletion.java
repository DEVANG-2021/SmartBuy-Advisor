package com.example.Project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a node in the Trie, with an array of children and an end-of-word marker
class TrieNode {
    TrieNode[] nextNodes; // Array for storing child nodes, one for each lowercase English letter
    boolean isEndOfWordFlag; // Flag to mark the end of a valid word
    List<String> suggestions; // List to store full product names as suggestions

    // Constructor initializes an array of TrieNode children and sets end-of-word flag to false
    public TrieNode() {
        nextNodes = new TrieNode[26]; // Array size of 26 for each letter in English alphabet
        isEndOfWordFlag = false; // Marks the end of the word as false by default
        suggestions = new ArrayList<>(); // Initialize suggestions list
    }
}

// Trie class that supports insertion of words and searching for words with a given prefix
class Trie {
    private TrieNode rootNode; // Root node of the Trie, where all words originate

    // Constructor initializes the Trie with a root node
    public Trie() {
        rootNode = new TrieNode(); // Root node is a new TrieNode instance
    }

    // Inserts a word into the Trie by traversing through each character
    public void insert(String word, String fullProductName) {
        TrieNode currentNode = rootNode; // Start insertion from the root node
        for (char character : word.toCharArray()) { // Loop through each character in the word
            if (character >= 'a' && character <= 'z') { // Only process lowercase English letters
                int index = character - 'a'; // Calculate array index for character
                if (currentNode.nextNodes[index] == null) { // If child node does not exist, create it
                    currentNode.nextNodes[index] = new TrieNode();
                }
                currentNode = currentNode.nextNodes[index]; // Move to child node
                if (currentNode.suggestions.size() < 20) { // Limit suggestions to top 5 for optimization
                    currentNode.suggestions.add(fullProductName);
                }
            }
        }
        currentNode.isEndOfWordFlag = true; // Mark the node as the end of a word
    }

    // Finds and returns suggestions for the given prefix
    public List<String> getSuggestions(String prefix) {
        TrieNode currentNode = rootNode; // Start searching from the root node

        // Traverse the Trie to reach the end node for the prefix
        for (char character : prefix.toCharArray()) {
            if (character >= 'a' && character <= 'z') { // Ensure character is a lowercase English letter
                int index = character - 'a'; // Calculate index for character
                if (currentNode.nextNodes[index] == null) { // If child node is null, no words match
                    return new ArrayList<>(); // Return an empty list as no words match the prefix
                }
                currentNode = currentNode.nextNodes[index]; // Move to child node for next character
            } else {
                return new ArrayList<>(); // If invalid character, return empty list
            }
        }
        return currentNode.suggestions; // Return the list of suggestions from the current node
    }
}

// Main class for loading data into the Trie, taking user input, and displaying results
public class WordCompletion {
    public static void main(String[] args) {
        Trie trieStructure = new Trie(); // Create a new instance of Trie
        String filePath = "products.csv"; // Path to the input CSV file

        // Load product names from the CSV file into the Trie for future searches
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { // Read each line from the file
                String[] columns = line.split(","); // Split the line by commas
                String productName = columns[0].trim(); // Extract the product name (first column)
                trieStructure.insert(productName.toLowerCase(), productName); // Insert the product name into the Trie
            }
        } catch (IOException e) { // Handle any file reading errors
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Prompt the user to enter a prefix to search for in the Trie
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter a prefix to search for words: ");
        String userPrefix = inputScanner.nextLine().trim().toLowerCase(); // Get and clean user input
        inputScanner.close(); // Close scanner to release resources

        List<String> foundSuggestions = trieStructure.getSuggestions(userPrefix); // Find suggestions with prefix

        // Display the found suggestions
        System.out.println("Suggestions for '" + userPrefix + "':");
        for (String suggestion : foundSuggestions) {
            System.out.println(suggestion); // Print each suggestion on a new line
        }
    }
}