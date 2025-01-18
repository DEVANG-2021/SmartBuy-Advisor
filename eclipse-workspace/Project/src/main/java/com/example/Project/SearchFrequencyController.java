//package com.example.Project;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//@RestController
//public class SearchFrequencyController {
//
//    // Endpoint to search for a word and get its frequency
//    @GetMapping("/search")
//    public Map<String, Object> search(@RequestParam String word) {
//        Map<String, Object> result = new HashMap<>();
//        String filePath = "products.csv"; // Path to your CSV file (update if necessary)
//
//        // Load data from the CSV file and perform search
//        StringBuilder csvData = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split("\t"); // Adjust delimiter as needed
//                csvData.append(parts[0]).append(" "); // Assuming product names are in the first column
//            }
//        } catch (IOException e) {
//            result.put("error", "Error reading the CSV file: " + e.getMessage());
//            return result;
//        }
//
//        String text = csvData.toString();
//
//        // KMP search logic
//        KMPAlgorithm kmp = new KMPAlgorithm();
//        int count = kmp.search(text, word);
//
//        // Return the search result and frequency
//        result.put("word", word);
//        result.put("occurrences", count);
//
//        // Frequency Tracker logic (to track how many times a word is searched)
//        FrequencyTracker tracker = new FrequencyTracker();
//        tracker.updateFrequency(word);
//        result.put("search_frequency", tracker.getFrequency(word));
//
//        return result;
//    }
//}


//new code

package com.example.Project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SearchFrequencyController {

    // HashMap to store the frequency of each word searched
    private static final Map<String, Integer> wordFrequencyMap = new HashMap<>();

    // Endpoint to search the word and get its frequency count
    @GetMapping("/search")
    public Map<String, Object> searchFrequency(@RequestParam String word) {
        // CSV file containing product names
        String filePath = "products.csv";
        StringBuilder csvData = new StringBuilder();

        // Load the CSV file and append product names to csvData
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming tab-separated values, adjust if needed
                csvData.append(parts[0]).append(" "); // Append product name to csvData
            }
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error reading the CSV file: " + e.getMessage());
            return errorResponse;
        }

        String text = csvData.toString();

        // Perform KMP search to find occurrences of the word in the CSV data
        KMPAlgorithm kmp = new KMPAlgorithm();
        int occurrences = kmp.search(text, word);

        // Increment the search frequency count
        int frequencyCount = wordFrequencyMap.getOrDefault(word, 0) + 1;
        wordFrequencyMap.put(word, frequencyCount);

        // Prepare the response in JSON format
        Map<String, Object> response = new HashMap<>();
        response.put("word", word);
        response.put("occurrences", occurrences);
        response.put("frequencyCount", frequencyCount);

        return response;
    }
}

