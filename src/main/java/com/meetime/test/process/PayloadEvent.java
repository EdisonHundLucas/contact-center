/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.meetime.test.dto.Event;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author edison
 */
/*
{
    "eventId": 1431776902,
    "subscriptionId": 3329277,
    "portalId": 49547190,
    "appId": 9393361,
    "occurredAt": 1742214467116,
    "subscriptionType": "contact.creation",
    "attemptNumber": 0,
    "objectId": 106651134132,
    "changeFlag": "CREATED",
    "changeSource": "INTEGRATION",
    "sourceId": "9393361"
}
*/
@Component
public class PayloadEvent {
    
    private static final Logger logger = Logger.getLogger(PayloadEvent.class.getName());
    
    public void processContactCreation(Map<String, Object> event) {
        try {
            String contactId = String.valueOf(event.get("objectId"));
            logger.log(Level.INFO, "Novo contato criado em HubSpot! ID: {0}", contactId);
            //Converte Objeto em JSON
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(event);            
            //Atribui o JSON ao obejto Event
            ObjectMapper objectMapper = new ObjectMapper();
            Event contactEvent = objectMapper.readValue(json, Event.class);
            logger.log(Level.INFO, "contactEvent: {0}", contactEvent.toString());

            //Criar eventual processamento do evento
            
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "Erro ao processar a criação do contato: {0}", e.getMessage());
            throw new RuntimeException("Erro ao processar a criação do contato", e);
        }
    }     
    
}
