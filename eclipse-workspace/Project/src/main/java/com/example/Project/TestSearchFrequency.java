package com.example.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestSearchFrequency {
    public static void main(String[] args) {
        System.out.println("Testing SearchFrequencyTracke functionality...\n");

        // Load smartwatch names from the CSV file
        StringBuilder csvData = new StringBuilder();
        String filePath = "products.csv"; // Ensure the file path is correct

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Adjust delimiter based on your CSV format
                csvData.append(parts[0]).append(" "); // Assuming smartwatch names are in the first column
            }
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        String sampleData = csvData.toString();
        System.out.println("Products loaded from CSV file: " + sampleData + "\n");

        // Tracker and KMP algorithm instances
        SearchFrequency tracker = new SearchFrequency();
        KMPAlgorithm kmp = new KMPAlgorithm();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Smartwatch Search Frequency Tracker");
        System.out.println("-----------------------------------");

        // Search loop
        while (true) {
            System.out.print("Enter the smartwatch name to search (or type 'exit' to quit): ");
            String word = scanner.nextLine();

            if (word.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the search tracker...");
                break;
            }

            // Count occurrences using KMP algorithm
            int count = kmp.search(sampleData, word);

            if (count > 0) {
                System.out.println("Occurrences of '" + word + "': " + count);
            } else {
                System.out.println("'" + word + "' not found in the data.");
            }

            // Update and display search frequency
            tracker.updateFrequency(word);
            System.out.println("Search frequency for '" + word + "': " + tracker.getFrequency(word));
            System.out.println();
        }

        scanner.close();
        System.out.println("Testing completed.");
    }
}
