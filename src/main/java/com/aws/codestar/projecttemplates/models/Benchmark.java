package com.aws.codestar.projecttemplates.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "benchmarks")
public class Benchmark {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isntance_id")
    public Instance instance;

    @Column(name = "date")
    public Date date;

    @Column(name = "implementation_id")
    public UUID implementationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_config_id")
    public MachineConfig machineConfig;
}
