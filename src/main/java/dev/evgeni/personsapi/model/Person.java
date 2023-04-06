package dev.evgeni.personsapi.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.evgeni.personsapi.validation.ValidEgn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Person {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Range(min = 0, max = 200, message = "i like ages from 0 to 200")
    private int age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonIgnoreProperties("person")
    private Address address;

    @Column(length = 32, columnDefinition = "varchar(32) default 'UNKNOWN'", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.UNKNOWN;

    @ManyToMany
    @JoinTable(name = "person_photo", joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id"))
    @JsonIgnoreProperties("persons")
    private Set<Photo> photos;

    @ValidEgn
    private String egnNumber;

    @OneToMany(mappedBy="author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("author")
    private Set<Comment> comments;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime modified;
}
