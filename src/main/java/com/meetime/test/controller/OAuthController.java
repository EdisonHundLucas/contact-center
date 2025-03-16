/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author edison
 */

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    
    private static final Logger logger = Logger.getLogger(OAuthController.class.getName());
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    private static final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";

    /*
    https://app.hubspot.com/oauth/authorize?client_id=d2d03417-4c86-4264-bc6b-dea3e6f7a1b4&redirect_uri=http://localhost:8080/oauth/callback&scope=crm.objects.contacts.write%20oauth%20crm.objects.contacts.read
    
    Redirect: http://localhost:8080/oauth/callback?code=d2d03417-4c86-4264-bc6b...
    
    Answer:
    {
        "token_type": "bearer",
        "refresh_token": "na1-8649-43d7-456c-a15c-305ec7f11101",
        "expires_in": 1800,
        "access_token": "CL-eitzZMhICAAEY7eTPFyDpr7YlKNGpvQQyFN11vnv8bmOCj4wOYbuXfRrcgSvCOgQAAABBQhSk2iDw4zfGTykbDG9Luae3qfxyGUoDbmExUgBaAGAAaOmvtiVwAA"
    }    
    */
    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        
        logger.log(Level.INFO, "----------------- callback start --------------------");
        logger.log(Level.INFO,"CODE: {0}", code);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d2d03417-4c86-4264-bc6b-dea3e6f7a1b4");
        params.add("client_secret", "509c958b-4d01-442c-bcff-6949574758a2");
        params.add("redirect_uri", "http://localhost:8080/oauth/callback");
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

        headers.setContentType(MediaType.APPLICATION_JSON);
        
        logger.log(Level.INFO,"Response: {0}", response.getBody());
        logger.log(Level.INFO, "----------------- callback end --------------------");
        return ResponseEntity.ok().headers(headers).body(response.getBody());
    }   
        
}
