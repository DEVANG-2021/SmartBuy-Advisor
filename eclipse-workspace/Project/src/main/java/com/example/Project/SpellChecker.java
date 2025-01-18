package com.example.Project;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.StringTokenizer;

class cuckoohashashing {
    private static final int TABLEZ = 2048; // Size of the hash table
    public String[] table_a; // Array to store words in the hash table
    public int[] wordCount; // Array to store the frequency of each word

    public cuckoohashashing() {
        table_a = new String[TABLEZ];
        wordCount = new int[TABLEZ]; // Initialize word count array
    }

    private int hashfunc1(String key) {
        return Math.abs(key.hashCode()) % TABLEZ;
    }

    private int hashfunc2(String key) {
        return (Math.abs(key.hashCode()) / TABLEZ) % TABLEZ;
    }

    // Insert word into the hash table using Cuckoo Hashing and track the word count
    public void t1_ist(String word) {
        int asdf = hashfunc1(word);
        int asdf_1 = hashfunc2(word);

        for (int i = 0; i < 10; i++) {
            if (table_a[asdf] == null) {
                table_a[asdf] = word;
                wordCount[asdf] = 1; // Set initial count to 1
                return;
            } else if (table_a[asdf].equals(word)) {
                wordCount[asdf]++; // Increment count if word is already present
                return;
            } else {
                String temp = table_a[asdf];
                table_a[asdf] = word;
                word = temp;
                asdf = hashfunc1(word);
            }
        }
    }

    // Check if the word exists in the hash table
    public boolean contains(String word) {
        return (table_a[hashfunc1(word)] != null && table_a[hashfunc1(word)].equals(word)) ||
                (table_a[hashfunc2(word)] != null && table_a[hashfunc2(word)].equals(word));
    }

    // Get the word count from the hash table
    public int getWordCount(String word) {
        int index = hashfunc1(word);
        if (table_a[index] != null && table_a[index].equals(word)) {
            return wordCount[index];
        } else {
            return 0;
        }
    }
}

public class SpellChecker {
    public cuckoohashashing table_vocab;

    public SpellChecker() {
        table_vocab = new cuckoohashashing();
    }

    // Load words from a CSV file into the hash table and count occurrences
    public void ld_vby(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Tokenize the line into words
                StringTokenizer tokenizer = new StringTokenizer(line, " ,\t");
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken().trim().toLowerCase();
                    table_vocab.t1_ist(word); // Insert word into the hash table and update count
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Calculate the edit distance between two words
    private int calculatedist(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    // Suggest words that are close to the misspelled word
    public List<String> acc2_alter(String word) {
        List<String> abc = new ArrayList<>();
        int t_hld = 2; // Threshold for edit distance

        for (String xyz : table_vocab.table_a) {
            if (xyz != null) {
                int distance = calculatedist(word, xyz);
                if (distance <= t_hld) {
                    abc.add(xyz);
                }
            }
        }

        abc = abc.stream()
                .sorted(Comparator.comparingInt(s -> calculatedist(word, s)))
                .collect(Collectors.toList());
        return abc;
    }

    public static void main(String[] args) {
        SpellChecker spel_chk = new SpellChecker();

        // Load words from a CSV file into the hash table
        spel_chk.ld_vby("D:\\UNIVERSITY OF WINDSOR\\TERM 1\\Advanced Computing Concepts\\ACC Project\\smartwatch_data.csv");

        // Display the word frequencies in the hash table
        //System.out.println("Loaded words and their frequencies:");
        for (int i = 0; i < spel_chk.table_vocab.table_a.length; i++) {
            if (spel_chk.table_vocab.table_a[i] != null) {
                String word = spel_chk.table_vocab.table_a[i];
                int count = spel_chk.table_vocab.getWordCount(word);
                // System.out.println("Word: " + word + ", Count: " + count);
            }
        }

        // Get user input for spell checking
        Scanner tocheck = new Scanner(System.in);
        System.out.print("Enter the word for checking: ");
        String word = tocheck.nextLine().trim().toLowerCase();

        // Check if the word is spelled correctly
        if (spel_chk.table_vocab.contains(word)) {
            System.out.println("The word is spelled correctly.");
        } else {
            System.out.println("Incorrect word. \nDid you mean:");

            List<String> sgg = spel_chk.acc2_alter(word);

            if (sgg.isEmpty()) {
                System.out.println("No suggestions found.");
            } else {
                sgg.forEach(System.out::println);
            }
        }

        tocheck.close();
    }
}
