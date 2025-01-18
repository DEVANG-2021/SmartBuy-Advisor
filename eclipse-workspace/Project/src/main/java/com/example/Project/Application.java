package com.example.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Project") // Add this line to ensure proper component scanning
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


//http://localhost:8080/wordcount?word=Apple (DONE) 

//http://localhost:8080/api/page-ranking/search?query=samsung   (DONE)

//http://localhost:8080/api/spell-checker/check?word=(word) (DONE)

//http://localhost:8080/api/validation/validate-email?email=Jeet23@gmail.com (DONE)

//http://localhost:8080/api/validation/validate-url?url=(URL Address) 

// http://localhost:8080/api/validation/extract-domain?csvFilePath=/path/to/your/file.csv&imageColumn=2

//http://localhost:8080/api/word-completion/get-suggestions?prefix=gar   (DONE)

// search frequency : http://localhost:8080/search?word=Gamin (DONE)

//http://localhost:8080/products/config?name=(product name)

//netstat -ano | findstr :8080
//taskkill /PID 6560 /F