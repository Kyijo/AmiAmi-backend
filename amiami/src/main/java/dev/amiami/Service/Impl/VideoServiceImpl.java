package dev.amiami.Service.Impl;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.AmiAmiVideo;
import dev.amiami.Model.Tag;
import dev.amiami.Repository.VideoRepository;
import dev.amiami.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    private final Storage storage;
    private static final String BUCKET_NAME = "amiami";

    public VideoServiceImpl(){
        this.storage = StorageOptions.getDefaultInstance().getService();
    }


    @Override
    public ResponseEntity<String> uploadVideo(MultipartFile videoFile, AmiAmiUser user, List<Tag> tags) {
        return null;
    }

    @Override
    public ResponseEntity<AmiAmiVideo> getVideoById(Long videoId) {
    return ResponseEntity.of(videoRepository.findById(videoId));
    }

    @Override
    public ResponseEntity<List<AmiAmiVideo>> getAllVideos() {
        return ResponseEntity.of(Optional.of(videoRepository.findAll()));
    }

    @Override
    public Optional<List<AmiAmiVideo>> getVideosByUser(AmiAmiUser user) {
        return videoRepository.findByUser(user);
    }
}
