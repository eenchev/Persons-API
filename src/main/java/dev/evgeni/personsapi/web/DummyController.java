package dev.evgeni.personsapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.evgeni.personsapi.config.DummyConfigProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/dummy")
@AllArgsConstructor
@NoArgsConstructor
public class DummyController {

    @Autowired
    private Environment env;

    @Autowired
    private DummyConfigProperties dummyProps;

    @Value("${dev.number}")
    private Integer number;

    @GetMapping("")
    private String returnValueFromConfig() {
        return env.getProperty("LOGNAMEBLa", "defaultUser");
    }
    
}
