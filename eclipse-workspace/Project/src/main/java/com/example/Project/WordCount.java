//This File is Created By Shivam Patel (Student Id : 110164737)

package com.example.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordCount {

    /**
     * Creates a "bad character" heuristic map for the Boyer-Moore pattern-matching algorithm.
     * This map stores the last index position of each character in the pattern,
     * helping to determine the shift distance in case of a mismatch.
     *
     * @param searchPattern The pattern string for which the heuristic map is created.
     * @return A map containing each character's last occurrence index in the pattern.
     */
    private static Map<Character, Integer> createBadCharMap(String searchPattern) {
        Map<Character, Integer> badCharMap = new HashMap<>();
        int patternLength = searchPattern.length();
        int index = 0;

        // Using a while loop to store the last occurrence index of each character in the pattern
        while (index < patternLength) {
            badCharMap.put(searchPattern.charAt(index), index);
            index++;
        }

        return badCharMap; // Return the map for use in pattern search
    }

    /**
     * Boyer-Moore algorithm implementation for searching a pattern within a given text.
     * Utilizes a "bad character" heuristic for efficient search.
     *
     * @param mainText The text where the pattern search will be conducted.
     * @param searchPattern The pattern string to search for in the main text.
     * @return The number of times the pattern appears in the main text.
     */
    static int performBoyerMooreSearch(String mainText, String searchPattern) {
        int patternLength = searchPattern.length();
        int textLength = mainText.length();
        Map<Character, Integer> badCharMap = createBadCharMap(searchPattern); // Preprocess the pattern

        int shiftPosition = 0; // Shift position of the pattern relative to the text
        int occurrenceCount = 0; // Counter for occurrences of the pattern

        // Loop over the text to search for the pattern using Boyer-Moore algorithm
        while (shiftPosition <= (textLength - patternLength)) {
            int patternIndex = patternLength - 1;

            // Compare characters from the end of the pattern to the beginning
            while (patternIndex >= 0 && searchPattern.charAt(patternIndex) == mainText.charAt(shiftPosition + patternIndex)) {
                patternIndex--; // Move left if characters match
            }

            // If patternIndex is -1, it means a match was found
            if (patternIndex < 0) {
                occurrenceCount++; // Increment count for each pattern occurrence found

                // Shift pattern according to the bad character heuristic for next potential match
                shiftPosition += (shiftPosition + patternLength < textLength) ?
                        patternLength - badCharMap.getOrDefault(mainText.charAt(shiftPosition + patternLength), -1) : 1;
            } else {
                // If mismatch, calculate shift using the bad character heuristic
                shiftPosition += Math.max(1, patternIndex - badCharMap.getOrDefault(mainText.charAt(shiftPosition + patternIndex), -1));
            }
        }

        return occurrenceCount; // Return the total number of pattern occurrences found
    }

    /**
     * Reads and concatenates the contents of a CSV file into a single lowercase string.
     *
     * @param fileLocation The path to the CSV file to be read.
     * @return A string containing the file's content, in lowercase.
     */
    static String readCSVFile(String fileLocation) {
        StringBuilder fileContent = new StringBuilder(); // To store the content of the file
        String fileLine;

        // Try to open and read the file line by line
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation))) {
            while ((fileLine = bufferedReader.readLine()) != null) {
                fileContent.append(fileLine).append("\n"); // Append each line to file content
            }
        } catch (IOException ioException) {
            System.err.println("Error reading the file: " + ioException.getMessage()); // Handle file read errors
        }

        return fileContent.toString().toLowerCase(); // Convert content to lowercase for case-insensitive search
    }

    /**
     * Counts the occurrences of a specified word within the CSV file content.
     *
     * @param searchWord The word to search for in the file.
     * @return The number of times the word appears in the file.
     */
    public static int getWordOccurrences(String searchWord) {
        String fileLocation = "products.csv";
        String fileContent = readCSVFile(fileLocation); // Read the CSV file content

        // Return zero occurrences if the file is empty or could not be read
        if (fileContent.isEmpty()) {
            System.out.println("The file is empty or could not be read.");
            return 0;
        }

        return performBoyerMooreSearch(fileContent, searchWord.toLowerCase()); // Search for the word (case-insensitive)
    }

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        String fileLocation = "products.csv"; // Location of the CSV file

        boolean continueSearch = true;
        while (continueSearch) {
            // Ask the user to input a word to search in the CSV file
            System.out.print("Enter the word to search: ");
            String searchWord = userInput.nextLine();

            // Validate that the input word is non-empty
            if (searchWord == null || searchWord.trim().isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty word.");
                continue; // Skip to next iteration if input is invalid
            }

            // Count the occurrences of the word in the CSV file
            int wordOccurrences = getWordOccurrences(searchWord);

            // Display the search result
            if (wordOccurrences > 0) {
                System.out.println("The word \"" + searchWord + "\" occurs " + wordOccurrences + " times in the file.");
            } else {
                System.out.println("The word \"" + searchWord + "\" was not found in the file.");
            }

            // Ask if the user wants to search another word
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Do you want to check another word's count? (yes/no): ");
                String response = userInput.nextLine().trim().toLowerCase();

                try {
                    if (response.equals("yes")) {
                        validInput = true; // User wants to search another word
                    } else if (response.equals("no")) {
                        validInput = true;
                        continueSearch = false; // End the program
                    } else {
                        throw new IllegalArgumentException("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } catch (IllegalArgumentException invalidResponse) {
                    System.out.println(invalidResponse.getMessage()); // Inform user of invalid input
                }
            }
        }
        userInput.close(); // Close the scanner
    }
}
