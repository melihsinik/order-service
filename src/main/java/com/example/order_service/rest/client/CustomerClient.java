package com.example.order_service.rest.client;

import javassist.NotFoundException;
import org.springframework.http.*;

import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@Service
public class CustomerClient {

    private String server = "http://localhost:8080";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;
    public CustomerClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }
    public boolean isVerified(int id) throws NotFoundException {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = null;
        try {
             responseEntity = rest.exchange(server + "customer/isVerified/" + id, HttpMethod.GET, requestEntity, String.class);

        }
        catch (HttpClientErrorException.Unauthorized er){
            return false;
        }
        catch (HttpClientErrorException.NotFound er){
             throw new NotFoundException(er.getMessage());
        }
        this.setStatus(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        System.out.println(status);
        return true;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
