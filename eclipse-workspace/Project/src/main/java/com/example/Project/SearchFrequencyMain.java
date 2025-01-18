package com.example.Project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SearchFrequencyMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SearchFrequency tracker = new SearchFrequency();
        StringBuilder csvData = new StringBuilder();

        // Load the CSV file
//        System.out.println("Enter the path to your CSV file: ");
        String filePath = "products.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Adjust delimiter based on CSV format
                csvData.append(parts[0]).append(" "); // Append product name to csvData
            }
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        String text = csvData.toString();

        // Search loop
        while (true) {
            System.out.println("\nEnter the word to search (or type 'exit' to quit): ");
            String word = scanner.nextLine();

            if (word.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            KMPAlgorithm kmp = new KMPAlgorithm();
            int count = kmp.search(text, word);

            System.out.println("Occurrences of '" + word + "': " + count);

            tracker.updateFrequency(word); // Correct usage
            System.out.println("Search frequency for '" + word + "': " + tracker.getFrequency(word));
        }

        scanner.close();
    }
}
