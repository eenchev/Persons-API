package dev.evgeni.personsapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAddValidPerson() throws Exception {
        mockMvc.perform(
            post("/persons")
            .header("Content-Type", "application/json")
            .content("""
                {
                    "name": "Ivan",
                    "age": 14,
                    "egnNumber": "8888888888"
                }
            """)
        ).andExpect(status().isCreated());
    }


    @Test
    void shouldThrowValidationErrorForInvalidPerson() throws Exception {
        mockMvc.perform(
            post("/persons")
            .header("Content-Type", "application/json")
            .content("""
                {
                    "name": "Ivan",
                    "age": -5,
                    "egnNumber": "888"
                }
            """)
        ).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.", null));
    }

    // {
    //     "id": "aa80b94c-6392-4b42-b624-7f76ca7507cf",
    //     "message": "Invalid Person Create",
    //     "errors": {
    //         "egnNumber": "Persons EGN shçould have exactly 10 chars",
    //         "age": "i like ages from 0 to 200"
    //     },
    //     "clazz": null
    // }
}
