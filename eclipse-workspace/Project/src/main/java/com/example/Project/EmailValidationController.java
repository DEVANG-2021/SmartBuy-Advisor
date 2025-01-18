package com.example.Project;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Create a class to represent the response in JSON format
class ValidationResponse {
    private String status;
    private String message;

    // Constructor
    public ValidationResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

@RestController
@RequestMapping("/api/validation")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailValidationController {

    // Regular expression for validating email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Method to validate email using regular expression
    @GetMapping("/validate-email")
    public ResponseEntity<ValidationResponse> validateEmail(@RequestParam("email") String email) {
        // Check if email is empty or null
        if (email == null || email.isEmpty()) {
            ValidationResponse response = new ValidationResponse("error", "Email is required");
            return ResponseEntity.badRequest().body(response);  // Return error message in JSON format
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        // Check if email matches the regex
        if (matcher.matches()) {
            ValidationResponse response = new ValidationResponse("success", "Valid email address");
            return ResponseEntity.ok(response);  // Return success message in JSON format
        } else {
            ValidationResponse response = new ValidationResponse("error", "Invalid email address");
            return ResponseEntity.badRequest().body(response);  // Return error message in JSON format
        }
    }
}
