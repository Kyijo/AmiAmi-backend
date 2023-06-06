package dev.amiami.Service;

import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.AmiAmiVideo;
import dev.amiami.Model.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface VideoService {

    ResponseEntity<String> uploadVideo(MultipartFile videoFile, AmiAmiUser userzz, List<Tag> tags);

    ResponseEntity<AmiAmiVideo> getVideoById(Long videoId);

    ResponseEntity<List<AmiAmiVideo>> getAllVideos();

    Optional<List<AmiAmiVideo>> getVideosByUser(AmiAmiUser user);

}
