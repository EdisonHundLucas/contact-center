/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.controller;

/**
 *
 * @author edison
 */
import com.meetime.test.config.AppConfig;
import jakarta.ejb.EJB;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class HelloController {

    private static final Logger logger = Logger.getLogger(HelloController.class.getName());
    
    @EJB
    private AppConfig appConfig;    
    
    @GetMapping("/hello")
    public String hello() {
        logger.log(Level.INFO, "AppName: {0}, ClientId: {1}, ClientSecret: {2}", 
                new Object[]{appConfig.getAppName(), appConfig.getClientId(), appConfig.getClientSecret()});
        
        logger.log(Level.INFO, "Hello, World!");
        return "Hello, World!";
    }
}