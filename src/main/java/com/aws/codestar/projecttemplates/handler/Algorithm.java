package com.aws.codestar.projecttemplates.handler;


import com.aws.codestar.projecttemplates.models.Implementation;
import com.aws.codestar.projecttemplates.models.Instance;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "algorithms")
public class Algorithm {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    public String name;

    @Column(name = "instances")
    @OneToMany(fetch = FetchType.LAZY)
    public List<Instance> instances = new ArrayList<Instance>();

    @Column(name = "implementations")
    @OneToMany(fetch = FetchType.LAZY)
    public List<Implementation> implementations = new ArrayList<Implementation>();

    public Algorithm(){}

    public Algorithm(String name) {
        this.name = name;
    }
}
