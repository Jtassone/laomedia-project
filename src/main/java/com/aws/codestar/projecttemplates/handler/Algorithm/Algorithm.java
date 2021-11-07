package com.aws.codestar.projecttemplates.handler.Algorithm;


import com.aws.codestar.projecttemplates.models.Implementation;
import com.aws.codestar.projecttemplates.models.Instance;
import java.util.*;
import java.util.UUID;

public class Algorithm {

    private UUID id;

    public String name;

    public String algorithmDetails;

    public List<Instance> instances = new ArrayList<Instance>();

    public List<Implementation> implementations = new ArrayList<Implementation>();

    public Algorithm(){}

    public Algorithm(String name) {
        this.name = name;
    }

    public Algorithm(String name, String algorithmDetails) {
        this.name = name;
        this.algorithmDetails = algorithmDetails;
    }

    public Algorithm(UUID id, String name, String algorithmDetails) {
        this.id = id;
        this.name = name;
        this.algorithmDetails = algorithmDetails;
    }
}
