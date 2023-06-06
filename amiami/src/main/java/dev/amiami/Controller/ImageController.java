package dev.amiami.Controller;

import dev.amiami.DTO.ImageDTO;
import dev.amiami.Model.AmiAmiImage;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.Tag;
import dev.amiami.Repository.TagRepository;
import dev.amiami.Repository.UserRepository;
import dev.amiami.Service.ImageService;
import dev.amiami.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagService tagService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestBody ImageDTO image) throws IOException {
        AmiAmiUser user = userRepository.findByUsername(image.getUsername()).get();
        List<Tag> tags = new ArrayList<>();
        for (String tagName : image.getTags()) {
         if(tagService.getTagByName(tagName).getBody() != null) {
             tags.add(tagService.getTagByName(tagName).getBody());
         }
        }
        return imageService.uploadImage(image.getFile(), user, image.getName(), tags);
    }

    @GetMapping("/getImageById/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable("imageId") Long imageId) {
        AmiAmiImage image = imageService.getImageById(imageId).getBody();

        if (image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
        }
    }

        @GetMapping("/getAllImages")
        public ResponseEntity<List<AmiAmiImage>> getAllImages() {
            return imageService.getAllImages();
        }

        @GetMapping("/getImagesByUser/{username}")
        public Optional<List<AmiAmiImage>> getImagesByUser(@PathVariable("username") String username) {
            Optional<AmiAmiUser> user = userRepository.findByUsername(username);
                return imageService.getImagesByUser(user.get());
            }
    }