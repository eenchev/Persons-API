package dev.evgeni.personsapi.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import dev.evgeni.personsapi.error.NotFoundObjectException;
import dev.evgeni.personsapi.mapper.CommentMapper;
import dev.evgeni.personsapi.mapper.PersonMapper;
import dev.evgeni.personsapi.model.Person;
import dev.evgeni.personsapi.service.PersonService;
import dev.evgeni.personsapi.validation.ObjectValidator;
import dev.evgeni.personsapi.web.dto.PersonResponse;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @MockBean
    private PersonService personService;

    @MockBean
    private ObjectValidator objectValidator;

    @MockBean
    private PersonMapper personMapper;
    
    @MockBean
    private CommentMapper commentMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchPresentPersonById() throws Exception {

        UUID presentId = UUID.fromString("e7f0595a-49dd-4e20-8a70-8532bce9e519");

        Person foundPerson = Person.builder()
            .id(presentId)
            .name("Ivan")
            .age(5)
            .build();
        
        when(
            personService.findById(eq(presentId))
        ).thenReturn(foundPerson);

        PersonResponse personResponse = PersonResponse.builder()
        .id(foundPerson.getId())
        .name(foundPerson.getName())
        .age(foundPerson.getAge())
        .build();

        when(
            personMapper.responseFromModel(eq(foundPerson))
        ).thenReturn(personResponse);

        mockMvc.perform(get("/persons/{id}", presentId.toString())
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.name").value("Ivan"))
        .andExpect(jsonPath("$.age").isNotEmpty())
        .andExpect(jsonPath("$.address").isEmpty())
        .andExpect(status().isOk());
    }

    @Test
    void fetchMissingPersonById() throws Exception {

        UUID missingId = UUID.fromString("e8f0595a-49dd-4e20-8a70-8532bce9e517");

        when(
            personService.findById(eq(missingId))
        ).thenThrow(NotFoundObjectException.class);

        mockMvc.perform(get("/persons/{id}", missingId.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
        .andExpect(status().isNotFound());
    }

    @Test
    void saveInvalidPersonNegativeAge() throws Exception {

        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("age", "cannot be negative");

        when(
            objectValidator.validate(any())
        ).thenReturn(validationErrors);

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "name": "Ivan",
                                "age": -5,
                                "address": null,
                                "egnNumber": "1234567899",
                                "gender": "MALE"
                            }   
                        """))
                .andExpect(status().isBadRequest());
    }
    
}
