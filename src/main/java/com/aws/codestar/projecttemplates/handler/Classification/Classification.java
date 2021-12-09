package com.aws.codestar.projecttemplates.handler.Classification;

import java.util.UUID;

public class Classification {


        private UUID id;

        public String name;

        public UUID parentClassificationId;

        public Classification(){}

        public Classification(String name) {
        this.name = name;
    }

        public Classification(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

    public Classification (String name, UUID parentClassificationId) {
        this.name = name;
        this.parentClassificationId = parentClassificationId;
    }

        public Classification(UUID id, String name, UUID parentClassificationId) {
            this.id = id;
            this.name = name;
            this.parentClassificationId = parentClassificationId;
        }

        public UUID getUUid(){
            return this.id;
        }
    }


