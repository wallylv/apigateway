package edu.cmu.mis.iccfb.controller;


//import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

@RestController
public class QuoteController {
    
    
    @RequestMapping("/api/quote/random")
    public Quote random() {
    	RestTemplate restTemplate = new RestTemplate();
    	String uri = "http://localhost:8081/api/quote/random";
    	Quote quote = restTemplate.getForObject(uri, Quote.class);
        return quote;
    }
    
    @RequestMapping("/api/quote/detail")
    public ResponseEntity<Quote[]> detail(String id) {
    	RestTemplate restTemplate = new RestTemplate();

    	String uri = "http://localhost:8081/api/quote/detail"+ id;
    	ResponseEntity<Quote[]> st = restTemplate.getForEntity(uri, Quote[].class);
      return st;
    }
    
    @RequestMapping("/api/quote/list")
    public ResponseEntity<Author[]> list() {    	
    	RestTemplate restTemplate = new RestTemplate();

    	String uri = "http://localhost:8082/api/quote/list";
    	ResponseEntity<Author[]> st = restTemplate.getForEntity(uri, Author[].class);
      return st;
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
    	RestTemplate restTemplate = new RestTemplate();
    	String uriauthor = "http://localhost:8082/api/quote";
    	String uriquote = "http://localhost:8081/api/quote";
    	ResponseEntity st = restTemplate.postForEntity(uriauthor, quote.getAuthor(), Long.class);
    	ResponseEntity st2 = restTemplate.postForEntity(uriquote, quote, Long.class);
    }
    
    
    public Quote fallback() {
        Quote q = new Quote();
        Author a = new Author("Confucius");
        q.setText("The superior man is modest in his speech, but exceeds in his actions.");
        q.setAuthor(a);
        return q; 
    }
}
