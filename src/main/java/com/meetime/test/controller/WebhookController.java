/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 *
 * @author edison
 */
/*
http://localhost:8080/hubspot/create-contact

Content-Type: application/json

{
    "properties": {
        "email": "amanda@email.com",
        "firstname": "Amanda",
        "lastname": "Silva",
        "phone": "55499999988888",
        "company": "Empresa ContactCenter"
    }
} 

Answer:
[
    {
        "eventId": 3400564480,
        "subscriptionId": 3329277,
        "portalId": 49547190,
        "appId": 9393361,
        "occurredAt": 1742077073905,
        "subscriptionType": "contact.creation",
        "attemptNumber": 0,
        "objectId": 106314056576,
        "changeFlag": "CREATED",
        "changeSource": "INTEGRATION",
        "sourceId": "9393361"
    }
]
*/
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger logger = Logger.getLogger(WebhookController.class.getName());
    
    @PostMapping
    public ResponseEntity<Void> receiveWebhook(@RequestBody List<Map<String, Object>> payload) {
        logger.log(Level.INFO, "----------------- Webhook start --------------------");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String payloadString = objectMapper.writeValueAsString(payload);
            logger.log(Level.INFO, "Payload String: {0}", payloadString);
        } catch (JsonProcessingException e) {
        }

        for (Map<String, Object> event : payload) {
            String eventType = (String) event.get("subscriptionType");

            if ("contact.creation".equals(eventType)) {
                this.processContactCreation(event);
            }
        }
        
        logger.log(Level.INFO, "----------------- Webhook end --------------------");
        return ResponseEntity.ok().build();
    }

    private void processContactCreation(Map<String, Object> event) {
        String contactId = String.valueOf(event.get("objectId"));
        logger.log(Level.INFO, "New contact created in HubSpot! ID: {0}", contactId);
        

    }    

}
