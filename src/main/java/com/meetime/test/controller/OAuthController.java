/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.controller;

import com.meetime.test.config.AppConfig;
import jakarta.ejb.EJB;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
/**
 *
 * @author edison
 */

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    
    private static final Logger logger = Logger.getLogger(OAuthController.class.getName());
    private final RestTemplate restTemplate = new RestTemplate();

    @EJB
    private AppConfig appConfig;    
    
    /*
    https://app.hubspot.com/oauth/authorize
            ?client_id={CLIENT_ID}
            &redirect_uri=http://localhost:8080/oauth/callback
            &scope=crm.objects.contacts.write%20oauth%20crm.objects.contacts.read
    
    Redirect: http://localhost:8080/oauth/callback?code=d2d03417-4...
    
    Answer:
    {
        "token_type": "bearer",
        "refresh_token": "na1-8649-4...",
        "expires_in": 1800,
        "access_token": "CL-eitzZMh..."
    }    
    */
@GetMapping("/callback")
public ResponseEntity<?> callback(@RequestParam("code") String code) {
    logger.log(Level.INFO, "----------------- callback start --------------------");
    logger.log(Level.INFO, "CODE: {0}", code);
    
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", appConfig.getAuthorizationGrantType());
    params.add("client_id", appConfig.getClientId());
    params.add("client_secret", appConfig.getClientSecret());
    params.add("redirect_uri", appConfig.getBasicUri() + "/oauth/callback");
    params.add("code", code);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

    try {
        //https://api.hubapi.com/oauth/v1/token
        ResponseEntity<Map> response = restTemplate.postForEntity(appConfig.getTokenUri(), request, Map.class);

        headers.setContentType(MediaType.APPLICATION_JSON);
        
        logger.log(Level.INFO, "Response: {0}", response.getBody());
        logger.log(Level.INFO, "----------------- callback end --------------------");
        return ResponseEntity.ok().headers(headers).body(response.getBody());
    } catch (HttpClientErrorException e) {
        logger.log(Level.SEVERE, "Erro ao trocar código por token: {0}", e.getResponseBodyAsString());
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    } catch (HttpServerErrorException e) {
        logger.log(Level.SEVERE, "Erro no servidor ao trocar código por token: {0}", e.getResponseBodyAsString());
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    } catch (RestClientException e) {
        logger.log(Level.SEVERE, "Erro ao tentar se comunicar com o servidor: {0}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar se comunicar com o servidor.");
    } catch (Exception e) {
        logger.log(Level.SEVERE, "Erro inesperado ao trocar código por token: {0}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado ao trocar código por token.");
    }
}   
        
}
