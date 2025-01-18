package com.example.Project;

import java.util.List;
import java.util.Scanner;

public class TestSpellChecker {

    public static void main(String[] args) {
        // Instantiate the AsthaDesaiSpellChecker
        SpellChecker spellChecker = new SpellChecker();

        // Load words from a CSV file into the hash table
        spellChecker.ld_vby("D:\\UNIVERSITY OF WINDSOR\\TERM 1\\Advanced Computing Concepts\\ACC Project\\smartwatch_data.csv"); // Update this with the actual path to your CSV file

        // Sample words to test the spell checker functionality
        Scanner scanner = new Scanner(System.in);

        // Test case 1: Correct word
        System.out.println("Test Case 1: Enter a correctly spelled word");
        System.out.print("Enter word: ");
        String word1 = scanner.nextLine().trim().toLowerCase();
        testWordExistence(spellChecker, word1);

        // Test case 2: Incorrect word
        System.out.println("\nTest Case 2: Enter an incorrect word");
        System.out.print("Enter word: ");
        String word2 = scanner.nextLine().trim().toLowerCase();
        testWordExistence(spellChecker, word2);

        // Test case 3: Checking word suggestions
        System.out.println("\nTest Case 3: Checking suggestions for a misspelled word");
        System.out.print("Enter a word for suggestions: ");
        String word3 = scanner.nextLine().trim().toLowerCase();
        testWordSuggestions(spellChecker, word3);

        scanner.close();
    }

    // Method to test if a word exists in the hash table
    private static void testWordExistence(SpellChecker spellChecker, String word) {
        if (spellChecker.table_vocab.contains(word)) {
            System.out.println("The word \"" + word + "\" is spelled correctly.");
        } else {
            System.out.println("The word \"" + word + "\" is not found in the dictionary.");
        }
    }

    // Method to test suggestions for a word
    private static void testWordSuggestions(SpellChecker spellChecker, String word) {
        List<String> suggestions = spellChecker.acc2_alter(word);
        if (suggestions.isEmpty()) {
            System.out.println("No suggestions found for the word \"" + word + "\".");
        } else {
            System.out.println("Suggestions for the word \"" + word + "\":");
            suggestions.forEach(System.out::println);
        }
    }
}
