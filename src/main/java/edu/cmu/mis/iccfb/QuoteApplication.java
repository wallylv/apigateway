package edu.cmu.mis.iccfb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import edu.cmu.mis.iccfb.controller.QuoteController;

@EnableCircuitBreaker
@SpringBootApplication
public class QuoteApplication {

    @Autowired
    
    public static void main(String[] args) {
        SpringApplication.run(QuoteApplication.class, args);
	}
}
