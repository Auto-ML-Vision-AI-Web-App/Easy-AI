package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.model.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Project {

    @Id
    Integer id;
    String name;
    String datasetUrl;
    LocalDateTime createTime;
    @OneToOne
    Account account;
    @OneToOne
    Model model;

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
}
