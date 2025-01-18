package com.example.Project;
import java.util.HashMap;

public class SearchFrequency {
    private HashMap<String, Integer> frequencyMap;

    public SearchFrequency() {
        frequencyMap = new HashMap<>();
    }

    // Update frequency of a word
    public void updateFrequency(String word) {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
    }

    // Get the frequency of a word
    public int getFrequency(String word) {
        return frequencyMap.getOrDefault(word, 0);
    }
}
