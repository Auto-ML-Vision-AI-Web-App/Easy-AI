package com.eavy.tag;

import com.eavy.project.Project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany
    private List<Project> projects = new ArrayList<>();
    private String name;

}
