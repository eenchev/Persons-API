package dev.evgeni.personsapi.model;

import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
public class Photo {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String originalFilename;
    private String contentType;

    @ManyToMany(mappedBy = "photos")
    @JsonIgnoreProperties("photos")
    private Set<Person> persons;

    @Lob
    @Column(length = 20971520)
    private byte[] content;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
}
