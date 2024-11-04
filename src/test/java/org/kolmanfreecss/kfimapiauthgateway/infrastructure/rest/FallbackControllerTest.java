package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * FallbackControllerTest class.
 * Used to test the FallbackController class.
 * 
 * @since 1.0
 */
@WebMvcTest(FallbackController.class)
class FallbackControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testFallback() {
    }
}
