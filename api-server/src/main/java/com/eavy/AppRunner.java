package com.eavy;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import com.eavy.project.Project;
import com.eavy.project.ProjectRepository;
import com.eavy.tag.Tag;
import com.eavy.tag.TagRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppRunner implements ApplicationRunner {

    private final AccountService accountService;
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public AppRunner(AccountService accountService, TagRepository tagRepository, ProjectRepository projectRepository) {
        this.accountService = accountService;
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

        Project prj1 = new Project("pretrained_model_test");
        prj1.setLoss(0.02);
        prj1.setAccuracy(0.99);
        prj1.setClasses(List.of("daisy", "sunflower"));

        Project prj2 = new Project("flower_class");
        prj2.setLoss(0.05);
        prj2.setAccuracy(0.88);
        prj2.setClasses(List.of("dandelion", "tulip"));

        Project prj3 = new Project("rose_and_daisy");
        prj3.setLoss(0.08);
        prj3.setAccuracy(0.77);
        prj3.setClasses(List.of("rose", "daisy"));

        projectRepository.save(prj1);
        projectRepository.save(prj2);
        projectRepository.save(prj3);

        Tag tag1 = new Tag("꽃");
        Tag tag2 = new Tag("해바라기");
        Tag tag3 = new Tag("꽃분류");
        Tag tag4 = new Tag("장미");
        Tag tag5 = new Tag("데이지");
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);
        tagRepository.save(tag5);

        prj1.addTag(tag1);
        prj1.addTag(tag2);
        prj1.addTag(tag3);

        prj2.addTag(tag1);
        prj2.addTag(tag3);

        prj3.addTag(tag1);
        prj3.addTag(tag4);
        prj3.addTag(tag5);

        account1.addProject(prj1);
        account2.addProject(prj2);
        account3.addProject(prj3);

        projectRepository.save(prj1);
        projectRepository.save(prj2);
        projectRepository.save(prj3);
    }
}
