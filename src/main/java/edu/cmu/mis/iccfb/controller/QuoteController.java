package edu.cmu.mis.iccfb.controller;


//import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestBody;

import edu.cmu.mis.iccfb.service.circuitbreaker;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

@EnableCircuitBreaker
@RestController
public class QuoteController {
	  public RestTemplate rest(RestTemplateBuilder builder) {
		    return builder.build();
		  }
	  
	  @Autowired
	  private circuitbreaker cbreaker;
	  
    @RequestMapping("/api/quote/random")
    public Quote random() {
    	return cbreaker.random();
    }
    
    @RequestMapping("/api/quote/detail")
    public Quote[] detail(String id) {
 
      return cbreaker.detail(id);
    }
    
    @RequestMapping("/api/quote/list")
    public Author[] list() {    	
    
      return cbreaker.list();
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public String saveQuote(@RequestBody Quote quote) {
      
      return cbreaker.save(quote);

    }

}
