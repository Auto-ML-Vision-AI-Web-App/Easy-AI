package com.eavy.tag;

import com.eavy.project.Project;
import com.eavy.project.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    public TagController(TagRepository tagRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
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

    // 태그가 존재할 경우 기존 태그에 프로젝트 추가
    // 존재하지 않는 경우 새로 태그 만들고 프로젝트 추가
    @PostMapping
    public ResponseEntity createOrUpdateTag(@RequestParam String projectName,
                                            @RequestParam String tagName) {
        Optional<Project> optionalProject = projectRepository.findByName(projectName);
        Optional<Tag> optionalTag = tagRepository.findByName(tagName);

        if (optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().body("Project '" + projectName + "' not exists");
        }

        Project project = optionalProject.get();
        Tag tag = optionalTag.orElseGet(() -> new Tag(tagName));
        tag.getProjects().add(project);
        project.getTags().add(tag);
        tagRepository.save(tag);

        return ResponseEntity.ok().build();
    }

}
