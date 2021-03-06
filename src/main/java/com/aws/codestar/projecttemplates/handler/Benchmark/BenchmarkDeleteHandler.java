package com.aws.codestar.projecttemplates.handler.Benchmark;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Implementation.ImplementationService;
import com.aws.codestar.projecttemplates.handler.User.UserEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.UserEventService;
import com.google.gson.Gson;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

public class BenchmarkDeleteHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Connection sqlConnection;
    Gson gson;

    public BenchmarkDeleteHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            String id = event.getPathParameters().get("UUID");
            BenchmarkService.deleteBenchmark(sqlConnection, id);
            String userName = event.getQueryStringParameters().get("userName");
            String eventDetails = "benchmark with the id " + id;
            UserEvent userEvent = new UserEvent(UUID.randomUUID(), userName, "DELETED", eventDetails);
            UserEventService.logUserEvent(sqlConnection, userEvent);
            response.setBody(gson.toJson(id));
            response.setStatusCode(200);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }
}

