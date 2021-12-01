package com.aws.codestar.projecttemplates.handler.Classification;

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

        public UUID getUUid(){
            return this.id;
        }
    }


