package dev.amiami.Repository;

import dev.amiami.Model.AmiAmiImage;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ImageRepository extends JpaRepository<AmiAmiImage, Long> {

    Optional<AmiAmiImage> findById(Long id);

    Optional<AmiAmiImage> findByTags(Tag tag);

    Optional<List<AmiAmiImage>> findByUser(AmiAmiUser user);
}
