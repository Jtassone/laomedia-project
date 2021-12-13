package com.aws.codestar.projecttemplates.utils;

import com.aws.codestar.projecttemplates.handler.User.UserEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserEventService {

    public static void logUserEvent(Connection sqlConnection, UserEvent event) throws SQLException {
        byte[] userEventId = UUIDUtil.getBytesFromUUID(event.id);
        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement( "INSERT INTO user_Events (id, user_name, event_verb, event_details) VALUES (?, ? , ? , ?)");
            preparedStatement.setBytes(1, userEventId);
            preparedStatement.setString(2, event.userName);
            preparedStatement.setString(3, event.eventVerb);
            preparedStatement.setString(4, event.eventDetails);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }
}
