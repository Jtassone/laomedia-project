package com.aws.codestar.projecttemplates.utils;

import com.aws.codestar.projecttemplates.handler.User.UserEvent;

import java.sql.Connection;
import java.sql.SQLException;

public class UserEventService {

    public static void logUserEvent(Connection sqlConnection, UserEvent event) throws SQLException {
        byte[] userEventId = UUIDUtil.getBytesFromUUID(event.id);
        try {
            String sqlQuery = "INSERT INTO user_Events (id, user_name, event_verb, event_details) VALUES (" + userEventId + ",\"" + event.userName + "\" , \"" + event.eventVerb + "\" , \"" + event.eventDetails + "\")";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }
}
