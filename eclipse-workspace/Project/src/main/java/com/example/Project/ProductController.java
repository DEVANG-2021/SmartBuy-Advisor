package com.example.Project;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @GetMapping("/config")
    public ResponseEntity<Product1> getProductDetails(@RequestParam String name) {
        Product1 product = findProductByName(name);
        if (product != null) {
            return ResponseEntity.ok(product); // Return product details as JSON
        } else {
            return ResponseEntity.status(404).body(null); // Return 404 if product not found
        }
    }

    // Method to read the CSV file and find the product by name
    private Product1 findProductByName(String name) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("products.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 10) { // Ensure there are 10 attributes in CSV row
                    // Check if the first column matches the product name
                    if (fields[0].equalsIgnoreCase(name)) {
                        // Construct the Product1 object
                        return new Product1(
                            fields[0],  // name
                            fields[1],  // price
                            fields[2],  // gps
                            fields[3],  // connectivity
                            fields[4],  // heartRateMonitor
                            fields[5],  // os
                            fields[6],  // touchscreen
                            fields[7],  // batteryLife
                            fields[8],  // storage
                            fields[9]   // imageURL
                        );
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if product is not found
    }
}
