package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.model.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Project {

    @Id
    @GeneratedValue
    Integer id;
    String name;
    LocalDateTime createTime = LocalDateTime.now();
    @ManyToOne
    Account account;
    @OneToOne
    Model model;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model ai) {
        this.model = ai;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
