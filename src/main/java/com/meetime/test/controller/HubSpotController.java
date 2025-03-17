/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.controller;

import com.meetime.test.config.AppConfig;
import jakarta.ejb.EJB;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.support.RetryTemplate;

/**
 *
 * @author edison
 */
/*
    https://app.hubspot.com/oauth/authorize
        ?client_id={CLIENT_ID}
        &redirect_uri=http://localhost:8080/oauth/callback
        &scope=crm.objects.contacts.write%20oauth%20crm.objects.contacts.read
*/
@RestController
@RequestMapping("/hubspot")
public class HubSpotController {
    
    private static final Logger logger = Logger.getLogger(HubSpotController.class.getName());
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    @EJB
    private AppConfig appConfig; 
    
    public HubSpotController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.retryTemplate = createRetryTemplate();
    }    
    
    /*
    http://localhost:8080/hubspot/contacts?access_token=CL-eitzZMhICA...
    
    {
        "results": [
            {
                "id": "106316914249",
                "properties": {
                    "createdate": "2025-03-15T21:52:35.138Z",
                    "email": "emailmaria@hubspot.com",
                    "firstname": "Maria",
                    "hs_object_id": "106316914249",
                    "lastmodifieddate": "2025-03-15T21:52:53.500Z",
                    "lastname": "Johnson (Sample Contact)"
                },
                "createdAt": "2025-03-15T21:52:35.138Z",
                "updatedAt": "2025-03-15T21:52:53.500Z",
                "archived": false
            }
        ]
    }    
    */
    @GetMapping("/contacts")
    public ResponseEntity<?> getContacts(@RequestParam("access_token") String accessToken) {
        logger.log(Level.INFO, "----------------- Contacts start --------------------");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        //https://api.hubapi.com/crm/v3/objects/contacts
        ResponseEntity<String> response = restTemplate.exchange(appConfig.getContactsUri(), HttpMethod.GET, entity, String.class);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        logger.log(Level.INFO,"Response: {0}", response.getBody());
        logger.log(Level.INFO, "----------------- Contacts end --------------------");
        return ResponseEntity.ok().headers(headers).body(response.getBody());
    }
    
    
    /*
    http://localhost:8080/hubspot/create-contact?access_token=CL-eitzZMhICA...
    
    Content-Type: application/json
    
    {
        "properties": {
            "email": "exemplo@email.com",
            "firstname": "João",
            "lastname": "Silva",
            "phone": "+55 11 99999-8888",
            "company": "Empresa X"
        }
    }    
    */
    @PostMapping("/create-contact")
    public ResponseEntity<?> createContact(@RequestParam("access_token") String accessToken, @RequestBody Map<String, Object> contactData) {
        logger.log(Level.INFO, "----------------- Create start --------------------");
        logger.log(Level.INFO, "Contact: {0}", contactData);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(contactData, headers);
        
        return retryTemplate.execute(context -> {
            try {
                //https://api.hubapi.com/crm/v3/objects/contacts
                ResponseEntity<Map> response = restTemplate.postForEntity(appConfig.getContactsUri(), request, Map.class);
                logger.log(Level.INFO,"Response: {0}", response.getBody());
                logger.log(Level.INFO, "----------------- Create end ----------------");
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            } catch (HttpClientErrorException.TooManyRequests e) {
                throw new RuntimeException("Rate limit excedido. Tentando novamente...", e);
            }
        });
    }

    private RetryTemplate createRetryTemplate() {
        return RetryTemplate.builder()
            .maxAttempts(5)
            .fixedBackoff(2000) // Aguarda 2 segundos antes de tentar novamente
            .retryOn(HttpClientErrorException.TooManyRequests.class)
            .build();
    }    
}