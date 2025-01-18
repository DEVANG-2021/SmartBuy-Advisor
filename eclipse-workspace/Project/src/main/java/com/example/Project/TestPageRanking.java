package com.example.Project;

import java.util.List;

public class TestPageRanking {

    public static void main(String[] args) {
        // Define the path to the CSV file
        String csvFilePath = "products.csv";

        // Example 1: Search for a simple query
        System.out.println("Test Case 1: Search for 'samsung'");
        List<Product> results1 = PageRanking.searchProducts(csvFilePath, "samsung");
        PageRanking.displayTopRankedProducts(results1);

        // Example 2: Search for multiple keywords
        System.out.println("\nTest Case 2: Search for 'heart rate monitor GPS'");
        List<Product> results2 = PageRanking.searchProducts(csvFilePath, "heart rate monitor GPS");
        PageRanking.displayTopRankedProducts(results2);

        // Example 3: Search for a non-existing keyword
        System.out.println("\nTest Case 3: Search for 'nonexistentkeyword'");
        List<Product> results3 = PageRanking.searchProducts(csvFilePath, "nonexistentkeyword");
        PageRanking.displayTopRankedProducts(results3);

        // Example 4: Search with an empty query
        System.out.println("\nTest Case 4: Search with an empty query");
        List<Product> results4 = PageRanking.searchProducts(csvFilePath, "");
        PageRanking.displayTopRankedProducts(results4);

        // Example 5: Test case with special characters
        System.out.println("\nTest Case 5: Search for '@#smartwatch!!'");
        List<Product> results5 = PageRanking.searchProducts(csvFilePath, "@#smartwatch!!");
        PageRanking.displayTopRankedProducts(results5);

        System.out.println("\nAll test cases executed.");
    }
}


