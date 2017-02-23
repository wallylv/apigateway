package edu.cmu.mis.iccfb.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;


@Service
public class circuitbreaker {

  private final RestTemplate restTemplate;

  public circuitbreaker(RestTemplate rest) {
    this.restTemplate = rest;
  }

  //circuit breaker for random quote
  @HystrixCommand(fallbackMethod = "reliablerandom")
  public Quote random() {
    URI uri = URI.create("http://localhost:8081/api/quote/random");

    return this.restTemplate.getForObject(uri, Quote.class);
  }

  public String reliablerandom() {
    return "Random Quote XXXX";
  }
 
 //circuit breaker for get the list of all authors
  @HystrixCommand(fallbackMethod = "reliablelist")
  public Author[] list() {
    URI uri = URI.create("http://localhost:8082/api/quote/list");
    ResponseEntity<Author[]> st = restTemplate.getForEntity(uri, Author[].class);
    return st.getBody();
  }

  public String reliablelist() {
    return "List XXXXX";
  }
  
  //circuit breaker for get the quote detail
  @HystrixCommand(fallbackMethod = "reliabledetail")
  public Quote[] detail(String id) {
    URI uri = URI.create("http://localhost:8081/api/quote/detail"+ id);
	ResponseEntity<Quote[]> st = restTemplate.getForEntity(uri, Quote[].class);
    return st.getBody();
  }

  public String reliabledetail() {
    return "Quote Detail XXXX";
  }
  
  //circuit breaker for save quote and author
  @HystrixCommand(fallbackMethod = "reliablesave")
  public String save(Quote quote) {
  	String uriauthor = "http://localhost:8082/api/quote";
  	String uriquote = "http://localhost:8081/api/quote";
  	ResponseEntity st = restTemplate.postForEntity(uriauthor, quote.getAuthor(), Long.class);
  	ResponseEntity st2 = restTemplate.postForEntity(uriquote, quote, Long.class);
  	
  	return "success";
  }

  public String reliablesave() {
    return "Pending";
  }

}
