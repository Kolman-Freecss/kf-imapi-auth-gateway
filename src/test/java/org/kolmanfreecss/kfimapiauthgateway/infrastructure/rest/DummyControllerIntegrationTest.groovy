package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * Important: This test needs to app to be running, so it's not a unit test. It's an integration test.
 * DummyControllerIntegrationTest
 *
 * @version 1.0
 * @author Kolman-Freecss
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Set up the full Spring application context
@Stepwise
// Ensures tests run in order, especially useful for rate-limited scenarios
class DummyControllerIntegrationTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    HttpEntity<String> requestEntity

    private static final int BURST_CAPACITY = 20

    // Setup with X-User-ID header
    def setup() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-ID", "test-user");
        requestEntity = new HttpEntity<>(null, headers);
    }

    /**
     * Test the rate limiter allows requests up to burst capacity
     * Use of Virtual Threads (JDK19) to simulate concurrent requests
     */
    def "Rate limiter allows requests up to burst capacity"() {
        given: "A request within the burst capacity"
        List<ResponseEntity<String>> responses = []

        when: "Sending requests equal to the burst capacity concurrently"
        List<Thread> threads = (1..BURST_CAPACITY).collect {
            Thread.start {
                ResponseEntity<String> response = restTemplate.exchange("/dummy/hello", HttpMethod.GET, requestEntity, String)
                responses.add(response)
            }
        }

        // Wait for all threads to complete
        threads.each { it.join() }

        then: "All requests are successful"
        responses.every { it.statusCode.value() == 200 }

        cleanup: "Sleep for 1 second to avoid exceeding limits in subsequent tests"
        sleep(1000) // Wait for 1 second
    }

    /**
     * Test the rate limiter blocks requests exceeding burst capacity
     * Use of Virtual Threads (JDK19) to simulate concurrent requests
     */
    def "Rate limiter blocks requests exceeding burst capacity"() {
        given: "A request that exceeds the burst capacity"
        List<ResponseEntity<String>> responses = [] // List to store all responses

        when: "Sending requests that exceed the limit"
        List<Thread> threads = (1..(BURST_CAPACITY + 15)).collect {
            Thread.start {
                ResponseEntity<String> response = restTemplate.exchange("/dummy/hello", HttpMethod.GET, requestEntity, String)
                synchronized (responses) { // Synchronize access to the list
                    responses.add(response) // Collect all responses
                }
            }
        }

        // Wait for all threads to complete
        threads.each { it.join() }

        then: "At least one response status is 429 (Too Many Requests)"
        responses.any { it.statusCode.value() == 429 }
    }


}
