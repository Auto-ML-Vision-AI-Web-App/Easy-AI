package com.eavy.tag;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import com.eavy.project.Project;
import com.eavy.project.ProjectDto;
import com.eavy.project.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/tags")
@RestController
public class TagController {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final AccountService accountService;

    public TagController(TagRepository tagRepository, ProjectRepository projectRepository, AccountService accountService) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
        this.accountService = accountService;
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
        return ResponseEntity.ok(byName.get().getProjects().stream().map(ProjectDto::new).collect(Collectors.toList()));
    }

    // 태그가 존재할 경우 기존 태그에 프로젝트 추가
    // 존재하지 않는 경우 새로 태그 만들고 프로젝트 추가
    @PostMapping
    public ResponseEntity createOrUpdateTag(Principal principal,
                                            @RequestParam String projectName,
                                            @RequestParam String tagName) {
        Account account = accountService.findByUserId(principal.getName()).get();
        Optional<Project> optionalProject = account.getProjects().stream().filter(p -> p.getName().equals(projectName)).findFirst();
        Optional<Tag> optionalTag = tagRepository.findByName(tagName);

        Project project;
        if (optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().body("Project '" + projectName + "' not exists");
        }
        project = optionalProject.get();

        Tag tag = optionalTag.orElseGet(() -> new Tag(tagName));
        tag.getProjects().add(project);
        project.getTags().add(tag);
        tagRepository.save(tag);

        return ResponseEntity.ok().build();
    }

}
