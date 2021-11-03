package com.aws.codestar.projecttemplates.handler.Classification;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.models.Implementation;
import com.aws.codestar.projecttemplates.models.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Classification {


        private String id;

        public String name;

        public String implementationDetails;

        public List<Algorithm> algorithms = new ArrayList<Algorithm>();

        public Classification parentClassification;

        public Classification(String id, String name, String classificationDetails) {
            this.id = id;
            this.name = name;
            this.implementationDetails = classificationDetails;
        }

        public Classification(String id, String name, String classificationDetails, Classification parentClassification) {
            this.id = id;
            this.name = name;
            this.implementationDetails = classificationDetails;
            this.parentClassification = parentClassification;
        }
    }


