package com.aws.codestar.projecttemplates.handler.Implementation;

import com.aws.codestar.projecttemplates.models.Benchmark;
import com.aws.codestar.projecttemplates.models.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Implementation {

    private UUID id;

    public String name;

    public String fileName;

    public String implementationDetails;

    public List<Benchmark> BenchmarkResults = new ArrayList<Benchmark>();


    public Implementation(){}

    public Implementation(String name) {
        this.name = name;
    }

    public Implementation(UUID id, String name, String fileName, String implementationDetails) {
        this.id = id;
        this.name = name;
        this.fileName = fileName;
        this.implementationDetails = implementationDetails;
    }
}
