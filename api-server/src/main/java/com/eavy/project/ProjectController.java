package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

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
        if(account.getProjects().stream().anyMatch(p -> p.getName().equals(projectName)))
            return ResponseEntity.badRequest().body("project name '" + projectName + "' already exists");
        Project project = new Project(projectName);
        account.addProject(project);
        projectRepository.save(project);
        return ResponseEntity.ok(projectName);
    }

}
