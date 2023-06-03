package dev.amiami.Service.Impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
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


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> uploadImage(MultipartFile imageFile, AmiAmiUser user, List<Tag> tags, String nameOfImage) throws IOException {
        try {
            if (imageFile.isEmpty()) {
               throw new CustomException("Failed to upload file: " + nameOfImage);
            }

            String destinationImage = "images/"+ nameOfImage+".jpg";
            BlobId blobId = BlobId.of(BUCKET_NAME, destinationImage);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, imageFile.getBytes());

            AmiAmiImage image = new AmiAmiImage();
            image.setName(nameOfImage);
            image.setUrl("https://storage.googleapis.com/" + BUCKET_NAME +"/"+ destinationImage);
            image.setUser(user);
            image.setTags(tags);
            imageRepository.save(image);

            return new ResponseEntity<>(
                    "https://storage.googleapis.com/" + BUCKET_NAME + "/" + destinationImage,
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + imageFile.getOriginalFilename(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<AmiAmiImage> getImageById(Long imageId) {
        return ResponseEntity.of(imageRepository.findById(imageId));
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
