package dev.amiami.Service.Impl;

import com.google.cloud.storage.*;
import dev.amiami.Exception.CustomException;
import dev.amiami.Model.AmiAmiImage;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.Tag;
import dev.amiami.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ImageServiceImpl implements dev.amiami.Service.ImageService {
    @Autowired
    private ImageRepository imageRepository;
    private final Storage storage;
    private static final String BUCKET_NAME = "amiami";

    public ImageServiceImpl(){
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile file, AmiAmiUser user, String nameOfImage, List<Tag> tags) throws IOException {
        if (file.isEmpty()) {
            throw new CustomException("File is empty");
        }

        // Determine the content type based on the file extension
        String contentType;
        String fileExtension = file.getOriginalFilename().split("\\.")[1].toLowerCase();
        if (fileExtension.equals("jpg") || fileExtension.equals("jpeg")) {
            contentType = "image/jpeg";
        } else if (fileExtension.equals("png")) {
            contentType = "image/png";
        } else {
            // Handle unsupported file types
            throw new CustomException("Unsupported file type");
        }

        // Specify the destination image path
        String destinationImage = "images/" + UUID.randomUUID() + "." + fileExtension;

        // Create a ByteArrayInputStream from the image bytes
        ByteArrayInputStream imageInputStream = new ByteArrayInputStream(file.getBytes());

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, destinationImage)
                        .setContentType(contentType)
                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                        .build(),
                imageInputStream);

        // Create the AmiAmiImage instance and set its properties
        AmiAmiImage image = new AmiAmiImage();
        image.setName(nameOfImage);
        image.setUrl("https://storage.googleapis.com/" + BUCKET_NAME + "/" + destinationImage);
        image.setUser(user);
        image.setTags(tags);

        // Save the image to the database
        AmiAmiImage savedImage = imageRepository.save(image);

        if (savedImage == null) {
            return ResponseEntity.unprocessableEntity().body("Couldn't upload the image. Something went wrong");
        }

        return ResponseEntity.ok("Image uploaded successfully.");
    }


    @Override
    public ResponseEntity<AmiAmiImage> getImageById(Long imageId) {
        Optional<AmiAmiImage> imageOptional = imageRepository.findById(imageId);
        if (imageOptional.isPresent()) {
            AmiAmiImage image = imageOptional.get();
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<AmiAmiImage>> getAllImages() {
        return ResponseEntity.of(Optional.of(imageRepository.findAll()));
    }

    @Override
    public Optional<List<AmiAmiImage>> getImagesByUser(AmiAmiUser user) {
        return imageRepository.findByUser(user);
    }
}
