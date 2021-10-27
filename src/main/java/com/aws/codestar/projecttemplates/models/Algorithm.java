package com.aws.codestar.projecttemplates.models;


import javax.persistence.*;
import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "algorithms")
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    public String name;

    @ElementCollection
    @JoinTable(
            name = "algorithm_instances",
            joinColumns = @JoinColumn(
                    name = "algorithm_id",
                    referencedColumnName = "id"
            )
    )
    @Column(name = "instance_id")
    public List<UUID> instances;

    @ElementCollection
    @JoinTable(
            name = "algorithm_implementations",
            joinColumns = @JoinColumn(
                    name = "algorithm_id",
                    referencedColumnName = "id"
            )
    )
    @Column(name = "implementation_id")
    public List<UUID> implementations;

}
