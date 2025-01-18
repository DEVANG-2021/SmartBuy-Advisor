package com.example.Project;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/validation")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your frontend
public class UrlValidation {

    // Regular expression to extract the domain from the URL
    private static final String DOMAIN_REGEX = "https?://([\\w.-]+)";

    /**
     * API endpoint to extract domain from a given URL.
     * @param url The full URL passed as a request parameter.
     * @return The extracted domain as JSON.
     */
    @GetMapping("/extract-domain")
    public ResponseEntity<DomainResponse> extractDomainFromUrl(@RequestParam("url") String url) {
        if (url == null || url.isEmpty()) {
            // Return error if URL is null or empty
            return ResponseEntity.badRequest().body(new DomainResponse(url, "Invalid or empty URL"));
        }

        // Extract domain using regex
        String domain = extractDomain(url);
        if (domain != null) {
            // Return the extracted domain
            return ResponseEntity.ok(new DomainResponse(url, domain));
        } else {
            // Return error if domain extraction fails
            return ResponseEntity.badRequest().body(new DomainResponse(url, "Failed to extract domain"));
        }
    }

    /**
     * Helper method to extract the domain from a URL using regex.
     * @param url The full URL.
     * @return The extracted domain, or null if not found.
     */
    private String extractDomain(String url) {
        Pattern pattern = Pattern.compile(DOMAIN_REGEX);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1); // Return the captured domain
        }
        return null;
    }

    /**
     * Class to represent the API response in JSON format.
     */
    public static class DomainResponse {
        private String imageUrl;
        private String domain;

        public DomainResponse(String imageUrl, String domain) {
            this.imageUrl = imageUrl;
            this.domain = domain;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }
}
