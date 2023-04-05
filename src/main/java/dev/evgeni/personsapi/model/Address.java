package dev.evgeni.personsapi.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String street;
    private int number;

    @OneToOne(mappedBy = "address")
    @JsonIgnoreProperties("address")
    private Person person;
}
