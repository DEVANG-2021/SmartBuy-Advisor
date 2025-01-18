package com.example.Project;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/spell-checker")
@CrossOrigin(origins = "http://localhost:3000")
public class SpellCheckerController {

    private final SpellChecker spellChecker;

    // Constructor to initialize spell checker
    public SpellCheckerController() {
        spellChecker = new SpellChecker();
        spellChecker.ld_vby("D:\\UNIVERSITY OF WINDSOR\\TERM 1\\Advanced Computing Concepts\\ACC Project\\smartwatch_data.csv"); // Load dictionary from CSV
    }

    // Endpoint to check the spelling of a word
    @GetMapping("/check")
    public SpellCheckResult checkWord(@RequestParam("word") String word) {
        // Remove leading and trailing quotes from the input word
        word = removeQuotes(word);

        // Get suggestions and check correctness
        List<String> suggestions = spellChecker.acc2_alter(word.toLowerCase());
        
        // Remove quotes from suggestions
        suggestions = suggestions.stream()
                                  .map(this::removeQuotes)
                                  .collect(Collectors.toList());

        boolean isCorrect = spellChecker.table_vocab.contains(word.toLowerCase());

        return new SpellCheckResult(word, isCorrect, suggestions);
    }

    // Helper method to remove leading and trailing quotes from a string
    private String removeQuotes(String input) {
        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }

    // Inner class to define the response structure
    public static class SpellCheckResult {
        private String word;
        private boolean isCorrect;
        private List<String> suggestions;

        public SpellCheckResult(String word, boolean isCorrect, List<String> suggestions) {
            this.word = word;
            this.isCorrect = isCorrect;
            this.suggestions = suggestions;
        }

        public String getWord() {
            return word;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }
    }
}