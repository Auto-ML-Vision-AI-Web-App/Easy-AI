package com.eavy.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/tags")
@RestController
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<String> getTags() {
        return tagRepository.findAll().stream().map(Tag::getName).collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public ResponseEntity getTag(@PathVariable String name) {
        Optional<Tag> byName = tagRepository.findByName(name);
        if(byName.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no project tagged as '" + name + "'");
        }
        return ResponseEntity.ok(byName.get().getProjects());
    }

}
