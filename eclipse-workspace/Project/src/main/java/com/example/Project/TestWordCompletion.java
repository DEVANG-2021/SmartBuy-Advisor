package com.example.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TestWordCompletion {

    public static void main(String[] args) {
        Trie trie = new Trie();

        // Path to your CSV file
        String filePath = "products.csv";  // Update with your actual file path

        // Load data into the Trie from the CSV file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                String productName = columns[0].trim(); // Assuming product name is in the first column
                trie.insert(productName.toLowerCase(), productName); // Insert product name into the Trie
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Scanner to take user input for searching words
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter a prefix to search for suggestions: ");
        String userPrefix = inputScanner.nextLine().trim().toLowerCase(); // Get and clean user input

        // Get suggestions based on the user input
        List<String> suggestions = trie.getSuggestions(userPrefix);

        // Display the suggestions
        if (suggestions != null && suggestions.size() > 0) {
            System.out.println("Suggestions for '" + userPrefix + "':");
            for (String suggestion : suggestions) {
                System.out.println(suggestion);  // Print each suggestion on a new line
            }
        } else {
            System.out.println("No suggestions found for '" + userPrefix + "'.");
        }

        inputScanner.close(); // Close the scanner after use
    }
}
