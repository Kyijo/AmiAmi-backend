package dev.amiami.Repository;

import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.AmiAmiVideo;
import dev.amiami.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VideoRepository extends JpaRepository<AmiAmiVideo, Long> {

    Optional<AmiAmiVideo> findById(Long id);

    Optional<AmiAmiVideo> findByTags(Tag tag);

    Optional<List<AmiAmiVideo>> findByUser(AmiAmiUser user);
}
