package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import com.eavy.tag.Tag;
import com.eavy.tag.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/projects")
@Controller
public class ProjectController {

    private final AccountService accountService;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @InitBinder("updatedProject")
    public void initBinderForProject(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("tags");
    }

    public ProjectController(AccountService accountService, ProjectRepository projectRepository, TagRepository tagRepository) {
        this.accountService = accountService;
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
    }

    @PostMapping
    public ResponseEntity createProject(Principal principal,
                                        @RequestParam String projectName) {
        Optional<Account> optionalAccount = accountService.findByUserId(principal.getName());
        Account account = optionalAccount.get();
        if(account.getProjects().stream().anyMatch(p -> p.getProjectName().equals(projectName)))
            return ResponseEntity.badRequest().body("project name '" + projectName + "' already exists");
        Project project = new Project(projectName);
        account.addProject(project);
        projectRepository.save(project);
        return ResponseEntity.ok(projectName);
    }

    @PatchMapping
    public ResponseEntity updateProject(Principal principal,
                                        @ModelAttribute(value = "updatedProject") Project updatedProject,
                                        @RequestParam(value = "tags", required = false) List<String> tagNames) {
        Optional<Account> optionalAccount = accountService.findByUserId(principal.getName());
        Account account = optionalAccount.get();
        Optional<Project> optionalProject =
                account.getProjects().stream().filter(p -> p.getProjectName().equals(updatedProject.getProjectName())).findFirst();
        if(optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().body("project name '" + updatedProject.getProjectName() + "' not exists");
        }

        Project project = optionalProject.get();
        project.setAccuracy(updatedProject.getAccuracy());
        project.setLoss(updatedProject.getLoss());
        project.setClasses(updatedProject.getClasses());
        if(tagNames != null) {
            tagNames.forEach(tagName -> {
                Optional<Tag> byName = tagRepository.findByName(tagName);
                byName.ifPresentOrElse(tag -> project.getTags().add(tag), () -> {
                    Tag tag = tagRepository.save(new Tag(tagName));
                    project.addTag(tag);
                });
            });
        }
        account.addProject(project);
        projectRepository.save(project);
        return ResponseEntity.ok(new ProjectDto(project));
    }

}
