package dev.evgeni.personsapi.serialization;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import dev.evgeni.personsapi.web.dto.PersonResponse;

@JsonTest
public class PersonResponseTest {

    @Autowired
    private JacksonTester<PersonResponse> personResponseJacksonTester;

    @Test
    void serializeInCorrectFormat() throws IOException {
        PersonResponse personResponse = PersonResponse.builder()
        .name("Ivan").build();

        JsonContent<PersonResponse> json = personResponseJacksonTester
                .write(personResponse);

        // isEqualToJsonValue
        assertThat(json).extractingJsonPathStringValue("$.name").isEqualTo("Ivan");
    }

    @Test
    void deserializePersonResponseFromJson() throws IOException {
        String responseObject = """
                    {
                        "id": "2fd5b2d6-1d44-483f-bcc9-71036c060351",
                        "name": "Ivan",
                        "age": 15
                    }
                """;

        String expectedName = "Ivan";

        PersonResponse personResponse = personResponseJacksonTester.parseObject(responseObject);

        assertThat(personResponse.getName()).isEqualTo(expectedName);
    }
    
}
