package dev.evgeni.personsapi.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dev.evgeni.personsapi.models.Photo;
import dev.evgeni.personsapi.repository.PhotoRepository;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository repo;

    @GetMapping("")
    public Set<UUID> getAllPhotos() {
        List<Photo> allPhotos = (List<Photo>) repo.findAll();

        Set<UUID> allPhotoIds = new HashSet<>();
        for (Photo photo : allPhotos) {
            allPhotoIds.add(photo.getId());
        }

        return allPhotoIds;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable String photoId) {
        Photo photo = repo.findById(UUID.fromString(photoId)).get();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, photo.getContentType())
                .body(photo.getContent());
    }

    @PostMapping("")
    public String handleImageUpload(@RequestParam("image") MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException {

        Photo newPhoto = Photo.builder().content(file.getBytes()).contentType(file.getContentType())
                .originalFilename(file.getOriginalFilename()).build();

        Photo photo = repo.save(newPhoto);

        return photo.getId().toString();
    }


}
