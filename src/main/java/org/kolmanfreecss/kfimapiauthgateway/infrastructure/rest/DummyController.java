package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dummy controller to test the application
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/")
public class DummyController {
    
    @GetMapping("/hello")
    public String hello() {
        log.info("Hello World!");
        return "Hello World!";
    }
    
    @GetMapping("/bye")
    public String bye() {
        log.info("Goodbye!");
        return "Goodbye!";
    }
    
    @GetMapping("/byeAdmin")
    public String byeAdmin() {
        log.info("Goodbye Admin!");
        return "Goodbye Admin!";
    }
    
    @GetMapping("/dummyException")
    public String dummyException() {
        log.info("Dummy exception");
        throw new RuntimeException("Dummy exception");
    }
    
}
