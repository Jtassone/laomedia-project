package com.aws.codestar.projecttemplates.handler.Implementation;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.aws.codestar.projecttemplates.handler.User.UserEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.S3Client;
import com.aws.codestar.projecttemplates.utils.UserEventService;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ImplementationPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Connection sqlConnection;
    Gson gson;
    S3Client s3Client;

    public ImplementationPostHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
        s3Client = new S3Client();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            JSONObject eventBody = new JSONObject(event.getBody());
            String name = eventBody.getString("name");
            String implementationDetails = eventBody.getString("implementationDetails");
            String algorithmId = eventBody.getString("algorithmId");
            Implementation implementation = new Implementation(name, implementationDetails, UUID.fromString(algorithmId));
            ImplementationService.saveImplementation(sqlConnection,s3Client, implementation);
            String userName = event.getQueryStringParameters().get("userName");
            String eventDetails = "Implementat6ion with the name" + name;
            UserEvent userEvent = new UserEvent(UUID.randomUUID(), userName, "CREATED", eventDetails);
            UserEventService.logUserEvent(sqlConnection, userEvent);
            response.setBody(gson.toJson(implementation));
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }

}
