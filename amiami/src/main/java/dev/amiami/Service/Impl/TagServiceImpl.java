package dev.amiami.Service.Impl;

import dev.amiami.Model.Tag;
import dev.amiami.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements dev.amiami.Service.TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public ResponseEntity<String> createTag(Tag tag) {
        if(tag.getName() != null && tag.getName().isEmpty()) {
            throw new RuntimeException("Tag name is required");
        }
        if(tagRepository.findByName(tag.getName()).isPresent()) {
            throw new RuntimeException("Tag already exists");
        }
        tagRepository.save(tag);
        return ResponseEntity.ok("Tag created successfully");
    }

    @Override
    public void deleteTagByName(Tag tag) {
        if(tag.getName() != null && tag.getName().isEmpty()) {
            throw new RuntimeException("Tag name is required");
        }
        tagRepository.delete(tag);
    }

    @Override
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @Override
    public ResponseEntity<Tag> getTagByName(Tag tag) {
       return ResponseEntity.ok(tagRepository.findByName(tag.getName()).orElseThrow(() -> new RuntimeException("Tag not found")));
    }
}
