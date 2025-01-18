package com.example.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WordCountController {

    @Autowired
    private WordCountService assignment3Service;

    @GetMapping("/wordcount")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<String, Object>> getWordCount(@RequestParam String word) {
        try {
            // Calling the service method to get the word count
            int count = assignment3Service.getWordOccurrences(word);

            // Creating a map to return the result as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("word", word);
            response.put("count", count);

            // Returning the result as a JSON object
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handling errors and returning a bad request response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
