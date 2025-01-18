package com.example.Project;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/word-completion")
@CrossOrigin(origins = "http://localhost:3000")
public class WordCompletionController {

    // Path to the input CSV file
    private static final String FILE_PATH = "products.csv";

    // Create a Trie structure
    private Trie trieStructure;

    // Constructor initializes the Trie and loads words from the CSV file
    public WordCompletionController() {
        trieStructure = new Trie();
        loadWordsIntoTrie();
    }

    // Load words from the CSV file into the Trie
    private void loadWordsIntoTrie() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { // Read each line from the file
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split the line into columns
                String[] columns = line.split(",");
                
                // Ensure there is at least one column before accessing it
                if (columns.length > 0) {
                    String productName = columns[0].trim(); // Extract the product name
                    if (!productName.isEmpty()) {
                        trieStructure.insert(productName.toLowerCase(), productName); // Insert into Trie
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    // API endpoint to get word suggestions based on a prefix
    @GetMapping("/get-suggestions")
    public ResponseEntity<SuggestionsResponse> getWordSuggestions(@RequestParam("prefix") String prefix) {
        List<String> suggestions = trieStructure.getSuggestions(prefix.toLowerCase());

        // Construct a structured JSON response
        SuggestionsResponse response = new SuggestionsResponse();
        response.setPrefix(prefix);
        response.setSuggestions(suggestions);
        response.setSuggestionCount(suggestions.size());

        return ResponseEntity.ok(response); // Return the structured response as JSON
    }

    // Response object to structure JSON output
    public static class SuggestionsResponse {
        private String prefix;
        private List<String> suggestions;
        private int suggestionCount;

        // Getters and setters for JSON serialization
        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }

        public int getSuggestionCount() {
            return suggestionCount;
        }

        public void setSuggestionCount(int suggestionCount) {
            this.suggestionCount = suggestionCount;
        }
    }

    // Represents a node in the Trie
    class TrieNode {
        TrieNode[] nextNodes;
        boolean isEndOfWordFlag;
        List<String> suggestions;

        public TrieNode() {
            nextNodes = new TrieNode[26];
            isEndOfWordFlag = false;
            suggestions = new ArrayList<>();
        }
    }

    // Trie class
    class Trie {
        private TrieNode rootNode;

        public Trie() {
            rootNode = new TrieNode();
        }

        public void insert(String word, String fullProductName) {
            TrieNode currentNode = rootNode;
            for (char character : word.toCharArray()) {
                if (character >= 'a' && character <= 'z') {
                    int index = character - 'a';
                    if (currentNode.nextNodes[index] == null) {
                        currentNode.nextNodes[index] = new TrieNode();
                    }
                    currentNode = currentNode.nextNodes[index];
                    if (currentNode.suggestions.size() < 20) {
                        currentNode.suggestions.add(fullProductName);
                    }
                }
            }
            currentNode.isEndOfWordFlag = true;
        }

        public List<String> getSuggestions(String prefix) {
            TrieNode currentNode = rootNode;
            for (char character : prefix.toCharArray()) {
                if (character >= 'a' && character <= 'z') {
                    int index = character - 'a';
                    if (currentNode.nextNodes[index] == null) {
                        return new ArrayList<>();
                    }
                    currentNode = currentNode.nextNodes[index];
                } else {
                    return new ArrayList<>();
                }
            }
            return currentNode.suggestions;
        }
    }
}
