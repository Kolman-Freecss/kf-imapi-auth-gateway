package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FallbackController
 * Used to define the FallbackController object.
 *
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@RestController
public class FallbackController {
    
    @GetMapping("/fallback")
    public String fallback() {
        return "Service is temporarily unavailable. Please try again later.";
    }
    
}
