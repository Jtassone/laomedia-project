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

        public UUID subClassificationId;

        public List<Classification> subClassifications;

        public Classification(){}

        public Classification(String name) {
        this.name = name;
    }

        public Classification(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Classification(String id, String name, UUID parentClassificationId) {
            this.id = id;
            this.name = name;
            this.subClassificationId = parentClassificationId;
        }
    }


