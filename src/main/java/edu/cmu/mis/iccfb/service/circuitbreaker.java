package edu.cmu.mis.iccfb.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.net.URI;

@EnableDiscoveryClient
@Service
public class circuitbreaker {

  private final RestTemplate restTemplate;
  
  private DiscoveryClient discoveryClient;

  public circuitbreaker(RestTemplate rest) {
    this.restTemplate = rest;
    
  }

  //circuit breaker for random quote
  @HystrixCommand(fallbackMethod = "reliablerandom")
  public Quote random() {
	JSONObject obj = new JSONObject (discoveryClient.getInstances("quote"));
	URI uri = URI.create(obj.getString("uri") + "/api/quote/random");
    return this.restTemplate.getForObject(uri, Quote.class);
  }

  public String reliablerandom() {
    return "Random Quote XXXX";
  }
 
 //circuit breaker for get the list of all authors
  
  @HystrixCommand(fallbackMethod = "reliablelist")
  public Author[] list() {
	JSONObject obj = new JSONObject (discoveryClient.getInstances("author"));
    URI uri = URI.create(obj.getString("uri") + "/api/quote/list");
    ResponseEntity<Author[]> st = restTemplate.getForEntity(uri, Author[].class);
    return st.getBody();
  }

  public String reliablelist() {
    return "List XXXXX";
  }
  
  //circuit breaker for get the quote detail
  @HystrixCommand(fallbackMethod = "reliabledetail")
  public Quote[] detail(String id) {
	JSONObject obj = new JSONObject (discoveryClient.getInstances("quote"));
	URI uri = URI.create(obj.getString("uri") + "/api/quote/detail" + id);
	ResponseEntity<Quote[]> st = restTemplate.getForEntity(uri, Quote[].class);
    return st.getBody();
  }

  public String reliabledetail() {
    return "Quote Detail XXXX";
  }
  
  //circuit breaker for save quote and author
  @HystrixCommand(fallbackMethod = "reliablesave")
  public String save(Quote quote) {
		JSONObject obj1 = new JSONObject (discoveryClient.getInstances("author"));
		JSONObject obj2 = new JSONObject (discoveryClient.getInstances("author"));
	    URI uriauthor = URI.create(obj1.getString("uri") + "/api/quote");
	    URI uriquote = URI.create(obj2.getString("uri") + "/api/quote");
  	ResponseEntity st = restTemplate.postForEntity(uriauthor, quote.getAuthor(), Long.class);
  	ResponseEntity st2 = restTemplate.postForEntity(uriquote, quote, Long.class);
  	
  	return "success";
  }

  public String reliablesave() {
    return "Pending";
  }

}
