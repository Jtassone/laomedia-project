package com.aws.codestar.projecttemplates.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "machine_config")
public class MachineConfig {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "core")
    public String core;

    @Column(name = "cpu")
    public String cpu;

    @Column(name = "l1")
    public String l1;

    @Column(name = "l2")
    public String l2;

    @Column(name = "l3")
    public String l3;

    @Column(name = "ram")
    public String ram;

    @Column(name = "num_threads")
    public String numThreads;
}
