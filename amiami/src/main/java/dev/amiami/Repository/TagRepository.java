package dev.amiami.Repository;

import dev.amiami.Model.AmiAmiImage;
import dev.amiami.Model.AmiAmiVideo;
import dev.amiami.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    List<Tag> findByImagesIn(List<AmiAmiImage> images);

    List<Tag> findByVideosIn(List<AmiAmiVideo> videos);
}
