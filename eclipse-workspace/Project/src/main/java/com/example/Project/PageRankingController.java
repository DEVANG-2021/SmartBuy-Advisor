//for all the products (it returns all the products without any limit)

//package com.example.Project;
//
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import java.util.List;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/api/page-ranking")
//@CrossOrigin(origins = "http://localhost:3000")
//public class PageRankingController {
//
//    private static final String csvFilePath = "products.csv";  // Hard-coded CSV file name and path
//
//    // Search for products based on a query
//    @GetMapping("/search")
//    public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query) {
//        // Call the method to search products
//        List<Product> rankedProducts = PageRanking.searchProducts(csvFilePath, query);
//        return ResponseEntity.ok(rankedProducts); // Return ranked products as a response
//    }
//
//    // Get top ranked products for a given query
//    @GetMapping("/top-ranked")
//    public ResponseEntity<List<Product>> getTopRankedProducts(@RequestParam("query") String query) {
//        // Call the method to search and rank products
//        List<Product> rankedProducts = PageRanking.searchProducts(csvFilePath, query);
//        
//        // Display top ranked products by extracting top 10
//        MaxHeap maxHeap = new MaxHeap();
//        for (Product product : rankedProducts) {
//            maxHeap.insert(product);
//        }
//        List<Product> topProducts = maxHeap.extractTop(10);
//
//        return ResponseEntity.ok(topProducts); // Return top 10 ranked products
//    }
//
//    // Endpoint to display a page ranking (ranking based on the keyword search)
//    @GetMapping("/display-top-ranked")
//    public ResponseEntity<List<Product>> displayTopRankedProducts(@RequestParam("query") String query) {
//        // Call method to search and rank products
//        List<Product> rankedProducts = PageRanking.searchProducts(csvFilePath, query);
//        MaxHeap maxHeap = new MaxHeap();
//        for (Product product : rankedProducts) {
//            maxHeap.insert(product); // Insert products into the heap
//        }
//        List<Product> topProducts = maxHeap.extractTop(10); // Get top 10 products
//
//        // Return the top products as a JSON response
//        return ResponseEntity.ok(topProducts); // Spring Boot automatically converts the List<Product> to JSON
//    }
//}

//new code

//(return only 15 products)

package com.example.Project;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/page-ranking")
@CrossOrigin(origins = "http://localhost:3000")
public class PageRankingController {

    private static final String csvFilePath = "products.csv";  // Hard-coded CSV file name and path

    // Search for products based on a query
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query) {
        // Call the method to search products
        List<Product> rankedProducts = PageRanking.searchProducts(csvFilePath, query);

        // Limit the result to 15 products
        int limit = 15;
        if (rankedProducts.size() > limit) {
            rankedProducts = rankedProducts.subList(0, limit);  // Limit to top 15 results
        }

        return ResponseEntity.ok(rankedProducts); // Return the limited ranked products as a response
    }

    // Get top ranked products for a given query
    @GetMapping("/top-ranked")
    public ResponseEntity<List<Product>> getTopRankedProducts(@RequestParam("query") String query) {
        // Call the method to search and rank products
        List<Product> rankedProducts = PageRanking.searchProducts(csvFilePath, query);
        
        // Limit the result to 15 products
        int limit = 50;
        if (rankedProducts.size() > limit) {
            rankedProducts = rankedProducts.subList(0, limit);  // Limit to top 15 results
        }

        // Display top ranked products by extracting top 15
        MaxHeap maxHeap = new MaxHeap();
        for (Product product : rankedProducts) {
            maxHeap.insert(product);
        }
        List<Product> topProducts = maxHeap.extractTop(limit);  // Get top 15 products

        return ResponseEntity.ok(topProducts); // Return top 15 ranked products
    }

    // Endpoint to display a page ranking (ranking based on the keyword search)
    @GetMapping("/display-top-ranked")
    public ResponseEntity<List<Product>> displayTopRankedProducts(@RequestParam("query") String query) {
        // Call method to search and rank products
        List<Product> rankedProducts = PageRanking.searchProducts(csvFilePath, query);
        MaxHeap maxHeap = new MaxHeap();
        for (Product product : rankedProducts) {
            maxHeap.insert(product); // Insert products into the heap
        }

        // Limit the result to 15 products
        int limit = 15;
        List<Product> topProducts = maxHeap.extractTop(limit);  // Get top 15 products

        // Return the top products as a JSON response
        return ResponseEntity.ok(topProducts); // Spring Boot automatically converts the List<Product> to JSON
    }
}
