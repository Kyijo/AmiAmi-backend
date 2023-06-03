package dev.amiami.Controller;

import dev.amiami.Model.Tag;
import dev.amiami.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagsController {

    @Autowired
    private TagService tagService;

    @PostMapping("/addTag")
    public ResponseEntity<Tag> addTag(String name, String urlForImage) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setBackgroundImage(urlForImage);
        tagService.createTag(tag);
        return ResponseEntity.ok(tag);
    }

    @PostMapping("/deleteTag")
    public ResponseEntity<Tag> deleteTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagService.deleteTagByName(tag);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/getAllTags")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok((List<Tag>) tagService.getAllTags().getBody());
    }

    @GetMapping("/getTagByName")
    public ResponseEntity<String> getTagByName(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return ResponseEntity.ok(tagService.getTagByName(tag).toString());
    }
}
