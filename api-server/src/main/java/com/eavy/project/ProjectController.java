package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.account.AccountRepository;
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

    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;

    public ProjectController(AccountRepository accountRepository, ProjectRepository projectRepository) {
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
    }

    @PostMapping
    public ResponseEntity createProject(Principal principal,
                                        @RequestParam String projectName) {
        Optional<Account> optionalAccount = accountRepository.findByUserId(principal.getName());
        Project project = new Project();
        Account account = optionalAccount.get();
        project.setAccount(account);
        account.getProjects().add(project);
        projectRepository.save(project);
        return ResponseEntity.ok(projectName);
    }

}
