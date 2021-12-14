package com.aws.codestar.projecttemplates.handler.Classification;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.User.UserEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.UserEventService;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class MergeClassificationHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Connection sqlConnection;
    Gson gson;

    public MergeClassificationHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        try {
            JSONObject eventBody = new JSONObject(event.getBody());
            String name = eventBody.getString("newClassificationName");
            UUID classificationId = UUID.fromString(eventBody.getString("classification1Id"));
            UUID oldClassificationId = UUID.fromString(eventBody.getString("classification2Id"));
            ClassificationService.mergeClassification(sqlConnection, classificationId, oldClassificationId, name);
            String userName = event.getQueryStringParameters().get("userName");
            String eventDetails = "classification with the name " + name;
            UserEvent userEvent = new UserEvent(UUID.randomUUID(), userName, "MERGED", eventDetails);
            UserEventService.logUserEvent(sqlConnection, userEvent);
            response.setBody("Success");
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }

}
