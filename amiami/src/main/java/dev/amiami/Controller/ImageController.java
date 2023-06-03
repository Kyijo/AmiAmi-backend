package dev.amiami.Controller;



import dev.amiami.Model.AmiAmiImage;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.Tag;
import dev.amiami.Repository.TagRepository;
import dev.amiami.Repository.UserRepository;
import dev.amiami.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private TagRepository tagRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("image") MultipartFile imageFile,
                                                   @RequestParam("ImageName") String nameOfImage,
                                                   @RequestParam("username") String username,
                                                   @RequestParam("tags") List<String> tags) throws IOException {

        Optional<AmiAmiUser> user = userRepository.findByUsername(username);
           if (user.isPresent()) {
                List<Tag> tag = new ArrayList<>();
                for (String tagName : tags) {
                    Optional<Tag> tagOptional = tagRepository.findByName(tagName);
                    if (tagOptional.isPresent()) {
                        tag.add(tagOptional.get());
                    }
                }
                return imageService.uploadImage(imageFile, user.get(), tag, nameOfImage);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
      }
      @GetMapping("/get/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable("imageId") Long imageId) {
            return imageService.getImageById(imageId);
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