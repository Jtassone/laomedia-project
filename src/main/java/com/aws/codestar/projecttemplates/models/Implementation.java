package com.aws.codestar.projecttemplates.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "implementation")
public class Implementation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    public String name;

    @Column(name = "implementation_details")
    public String implementationDetails;

    @ElementCollection
    @JoinTable(
            name = "implementation_benchmark_results",
            joinColumns = @JoinColumn(
                    name = "implementation_id",
                    referencedColumnName = "id"
            )
    )
    @Column(name = "benchmark_id")
    public List<UUID> benchmarks;


}
