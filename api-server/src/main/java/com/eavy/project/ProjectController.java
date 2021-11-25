package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/projects")
@Controller
public class ProjectController {

    private final AccountService accountService;
    private final ProjectRepository projectRepository;

    public ProjectController(AccountService accountService, ProjectRepository projectRepository) {
        this.accountService = accountService;
        this.projectRepository = projectRepository;
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
                                        Project updatedProject) {
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
        account.addProject(project);
        projectRepository.save(project);
        return ResponseEntity.ok(new ProjectDto(project));
    }

}
