package com.aws.codestar.projecttemplates.handler.Classification;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.models.Implementation;
import com.aws.codestar.projecttemplates.models.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Classification {


        private UUID id;

        public String name;

        public UUID subClassificationId;

        public Classification(){}

        public Classification(String name) {
        this.name = name;
    }

        public Classification(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

    public Classification (String name, UUID subClassificationId) {
        this.name = name;
        this.subClassificationId = subClassificationId;
    }

        public Classification(UUID id, String name, UUID parentClassificationId) {
            this.id = id;
            this.name = name;
            this.subClassificationId = parentClassificationId;
        }
    }


