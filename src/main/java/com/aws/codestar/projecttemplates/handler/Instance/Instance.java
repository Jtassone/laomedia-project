package com.aws.codestar.projecttemplates.handler.Instance;

import java.util.UUID;

public class Instance {

    public UUID id;

    public String name;

    public String instanceFileString;

    public UUID algorithmId;

    public UUID implementationId;

    public Instance(UUID id, String name, UUID algorithmId, UUID implementationId) {
        this.id = id;
        this.name = name;
        this.algorithmId = algorithmId;
        this.implementationId = implementationId;
    }

    public Instance(UUID id, String name, UUID algorithmId, UUID implementationId, String instanceFileString) {
        this.id = id;
        this.name = name;
        this.algorithmId = algorithmId;
        this.implementationId = implementationId;
        this.instanceFileString = instanceFileString;
    }

}