package dev.amiami.Service;


import dev.amiami.Model.AmiAmiImage;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ImageService {

    ResponseEntity<String> uploadImage(MultipartFile imageFile, AmiAmiUser user, List<Tag> tags, String nameOfImage) throws IOException;

    ResponseEntity<AmiAmiImage> getImageById(Long imageId);

    ResponseEntity<List<AmiAmiImage>> getAllImages();

    Optional<List<AmiAmiImage>> getImagesByUser(AmiAmiUser user);
}
