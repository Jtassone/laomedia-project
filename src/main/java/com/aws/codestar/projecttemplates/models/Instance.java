package com.aws.codestar.projecttemplates.models;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Instance")
public class Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

}
