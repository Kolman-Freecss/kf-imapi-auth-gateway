package org.kolmanfreecss.kfimapiauthgateway.infrastructure.rest

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * DummyControllerTest
 *
 * @version 1.0
 * @author Kolman-Freecss
 */
@WebMvcTest(DummyController) // Only the controller and related MVC components are loaded
class DummyControllerTest extends Specification {

    MockMvc mockMvc

    def setup() {
        // No need for MockitoAnnotations.openMocks(this) in this case
        mockMvc = MockMvcBuilders.standaloneSetup(new DummyController()).build()
    }

    def "test dummy Hello World"() {
        when: "The controller is called"
        def response = mockMvc.perform(get("/hello"))

        then: "The response is successful"
        response.andExpect(MockMvcResultMatchers.status().isOk())
    }

}
