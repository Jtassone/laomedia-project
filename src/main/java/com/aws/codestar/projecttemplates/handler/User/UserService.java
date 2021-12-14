package com.aws.codestar.projecttemplates.handler.User;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.utils.UUIDUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserService {

    public static List<UserEvent> getUserEvents(Connection sqlConnection, String name) throws SQLException {
        List<UserEvent> userEvents = null;
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("SELECT * from user_events where name = \"" + name + "\"")) {
            while (sqlResponse.next()) {
                byte[] sqlIdBytes = sqlResponse.getBytes("id");
                UUID id = UUIDUtil.getUUIDFromBytes(sqlIdBytes);
                String userName = sqlResponse.getString("user_name");
                String eventVerb = sqlResponse.getString("event_verb");
                String eventDetails = sqlResponse.getString("event_details");
                UserEvent event = new UserEvent(id, userName, eventVerb, eventDetails);
                userEvents.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        return userEvents;
    }
}
