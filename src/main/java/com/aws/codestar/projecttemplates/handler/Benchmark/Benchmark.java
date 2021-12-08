package com.aws.codestar.projecttemplates.handler.Benchmark;

import java.util.UUID;

public class Benchmark {


    private UUID id;

    public String name;

    public String benchmarkDetails;

    public UUID implementationId;

    public Benchmark(){}


    public Benchmark(String name) {
        this.name = name;
    }

    public Benchmark(String name, String benchmarkDetails) {
        this.name = name;
        this.benchmarkDetails = benchmarkDetails;
    }

    public Benchmark( String name, String benchmarkDetails, UUID implementationId) {
        this.name = name;
        this.benchmarkDetails = benchmarkDetails;
        this.implementationId = implementationId;
    }

    public Benchmark(UUID id, String name, String benchmarkDetails, UUID implementationId) {
        this.id = id;
        this.name = name;
        this.benchmarkDetails = benchmarkDetails;
        this.implementationId = implementationId;
    }
}
