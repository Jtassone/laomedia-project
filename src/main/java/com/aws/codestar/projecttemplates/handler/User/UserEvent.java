package com.aws.codestar.projecttemplates.handler.User;

import java.util.UUID;

public class UserEvent {

    public UUID id;

    public String userName;

    public String eventVerb;

    public String eventDetails;

    public UserEvent(UUID id, String userName, String eventVerb, String eventDetails) {
        this.id = id;
        this.userName = userName;
        this.eventVerb = eventVerb;
        this.eventDetails = eventDetails;
    }
}
