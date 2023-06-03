package dev.amiami.Service;

import dev.amiami.Model.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    ResponseEntity<String> createTag(Tag tag);

    void deleteTagByName(Tag tag);

    ResponseEntity<List<Tag>> getAllTags();

    ResponseEntity<Tag> getTagByName(Tag tag);
}
