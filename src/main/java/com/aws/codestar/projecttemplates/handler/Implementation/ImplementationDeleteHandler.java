package com.aws.codestar.projecttemplates.handler.Implementation;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.User.UserEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.S3Client;
import com.aws.codestar.projecttemplates.utils.UserEventService;
import com.google.gson.Gson;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

public class ImplementationDeleteHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    S3Client s3Client;
    Connection sqlConnection;
    Gson gson;

    public ImplementationDeleteHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
        this.s3Client = new S3Client();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            String id = event.getPathParameters().get("UUID");
            ImplementationService.deleteImplementation(sqlConnection, s3Client, id);
            response.setBody(gson.toJson(id));
            String userName = event.getQueryStringParameters().get("userName");
            String eventDetails = "Implementat6ion with the id" + id;
            UserEvent userEvent = new UserEvent(UUID.randomUUID(), userName, "DELETED", eventDetails);
            UserEventService.logUserEvent(sqlConnection, userEvent);
            response.setStatusCode(200);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }
}

