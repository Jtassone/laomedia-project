package com.aws.codestar.projecttemplates.handler.Implementation;

import com.aws.codestar.projecttemplates.models.Benchmark;
import com.aws.codestar.projecttemplates.models.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Implementation {

    private UUID id;

    public String name;

    public String implementationDetails;

    public UUID algorithmId;

    public Implementation(){}

    public Implementation(String name) {
        this.name = name;
    }

    public Implementation(UUID id, String name, String implementationDetails, UUID algorithmId) {
        this.id = id;
        this.name = name;
        this.implementationDetails = implementationDetails;
        this.algorithmId = algorithmId;
    }

    public Implementation(String name, String implementationDetails, UUID algorithmId) {
        this.name = name;
        this.implementationDetails = implementationDetails;
        this.algorithmId = algorithmId;
    }
}
