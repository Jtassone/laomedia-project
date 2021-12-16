package com.aws.codestar.projecttemplates.handler.Algorithm;

import java.util.UUID;

public class Algorithm {

    public UUID id;

    public String name;

    public String algorithmDetails;

    public UUID classificationId;

    public Algorithm(){}

    public Algorithm(String name) {
        this.name = name;
    }

    public Algorithm(String name, String algorithmDetails) {
        this.name = name;
        this.algorithmDetails = algorithmDetails;
    }

    public Algorithm( String name, String algorithmDetails, UUID classificationId) {
        this.name = name;
        this.algorithmDetails = algorithmDetails;
        this.classificationId = classificationId;
    }

    public Algorithm(UUID id, String name, String algorithmDetails, UUID classificationId) {
        this.id = id;
        this.name = name;
        this.algorithmDetails = algorithmDetails;
        this.classificationId = classificationId;
    }
}
