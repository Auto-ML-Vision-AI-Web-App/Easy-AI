package com.eavy;

import com.eavy.account.Account;
import com.eavy.account.AccountRepository;
import com.eavy.account.AccountService;
import com.eavy.project.Project;
import com.eavy.project.ProjectRepository;
import com.eavy.tag.Tag;
import com.eavy.tag.TagRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    private final ResourceLoader resourceLoader;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public AppRunner(ResourceLoader resourceLoader, AccountService accountService, AccountRepository accountRepository, TagRepository tagRepository, ProjectRepository projectRepository) {
        this.resourceLoader = resourceLoader;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account1 = new Account("h01010", "1234");
        Account account2 = new Account("db17", "asdf");
        Account account3 = new Account("kdh15", "qwer");
        accountService.signUp(account1);
        accountService.signUp(account2);
        accountService.signUp(account3);

        Project prj1 = new Project("dog_cat");
        Project prj2 = new Project("cat_frog");
        projectRepository.save(prj1);
        projectRepository.save(prj2);
        Tag tag1 = new Tag("dog");
        Tag tag2 = new Tag("cat");
        Tag tag3 = new Tag("frog");
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        prj1.addTag(tag1);
        prj1.addTag(tag2);
        prj2.addTag(tag2);
        prj2.addTag(tag3);
        account2.addProject(prj1);
        account2.addProject(prj2);

        projectRepository.save(prj1);
        projectRepository.save(prj2);
    }
}
