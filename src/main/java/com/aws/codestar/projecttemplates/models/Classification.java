package com.aws.codestar.projecttemplates.models;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "classification")
public class Classification {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    public String name;

    @Column(name = "sub_classifications")
    @OneToMany(fetch = FetchType.LAZY)
    public List<Classification> subClassification;

    @Column
    @OneToMany(fetch = FetchType.LAZY)
    public List<Algorithm> algorithms;

}
