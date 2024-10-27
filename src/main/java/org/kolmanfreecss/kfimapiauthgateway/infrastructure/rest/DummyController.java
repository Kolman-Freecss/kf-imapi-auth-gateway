package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dummy controller to test the application
 * @since 1.0
 */
@RestController
@RequestMapping("/")
public class DummyController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
    
    @GetMapping("/bye")
    public String bye() {
        return "Goodbye!";
    }
    
    @GetMapping("/byeAdmin")
    public String byeAdmin() {
        return "Goodbye Admin!";
    }
    
}
