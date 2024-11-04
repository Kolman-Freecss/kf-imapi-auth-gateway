package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * DummyControllerIntegrationTest
 *
 * @version 1.0
 * @author Kolman-Freecss
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Set up the full Spring application context
@Stepwise // Ensures tests run in order, especially useful for rate-limited scenarios
class DummyControllerIntegrationTest extends Specification{
    
    @Autowired
    TestRestTemplate restTemplate
    
    def "Rate limiter test against Hello World (test rate limiter allows 20 requests in 1 second)"() {
        when: "Sending 20 requests to the rate limited endpoint"
        def responses = (1..20).collect {
            restTemplate.getForEntity("/dummy/hello", String)
        }

        then: "All requests are successful"
        responses.every { it.statusCode.value() == 200 }
    }
    
    def "Rate limiter test against Hello World (test rate limiter blocks the 21st request)"() {
        when: "Sending a 21st request to the rate limited endpoint"
        ResponseEntity<String> response = restTemplate.getForEntity("/dummy/hello", String)
        
        then: "The response status is 429 (Too Many Requests)"
        response.statusCode.value() == 429
    }
    
}
