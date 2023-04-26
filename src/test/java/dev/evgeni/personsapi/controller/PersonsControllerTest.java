package dev.evgeni.personsapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import dev.evgeni.personsapi.mapper.PersonMapper;
import dev.evgeni.personsapi.models.Person;
import dev.evgeni.personsapi.service.PersonService;
import dev.evgeni.personsapi.validation.ObjectValidator;
import dev.evgeni.personsapi.web.JwtFilter;
import dev.evgeni.personsapi.web.PersonController;

@WebMvcTest(controllers = PersonController.class,  
    excludeAutoConfiguration = {SecurityAutoConfiguration.class},
    excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)
)
public class PersonsControllerTest {

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;

    @MockBean
    private ObjectValidator validator;

    @InjectMocks
    private PersonController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldHavePaginationOnFetchAll() throws Exception {
        // when service called fetchAll -> return empty result
        Page<Person> emptyPersonPage = new PageImpl<>(
            Collections.emptyList(), 
            PageRequest.of(0, 10) , 0);
        when(personService.fetchAll(0, 10)).thenReturn(emptyPersonPage);
       
        // when mapper called with .... -> return 
        mockMvc.perform(
            get("/persons")
            // .param("currPage", "5")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pagination.currentPage").value(0))
            .andExpect(jsonPath("$.pagination.pageSize").value(10))
            .andExpect(jsonPath("$.pagination.totalElements").value(0));
    }

    @Test
    void shouldReturnBadRequestWithErrors() throws Exception {

        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("age", "cannot be negative");

        when(
            validator.validate(any())
        ).thenReturn(validationErrors);
        
        mockMvc.perform(post("/persons")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
                "name": "Ivan",
                "age": -5,
                "egnNumber": "88888888888"
            }
            """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.age").value("cannot be negative"));
    }
    
}
