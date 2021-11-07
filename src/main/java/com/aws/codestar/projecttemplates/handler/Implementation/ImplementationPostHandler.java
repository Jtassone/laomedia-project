package com.aws.codestar.projecttemplates.handler.Implementation;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmService;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ImplementationPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Connection sqlConnection;
    Gson gson;

    public ImplementationPostHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
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
            ImplementationService.postImplementation(sqlConnection, implementation);
            System.out.println(implementation);
            JSONObject implementationJson = new JSONObject(implementation);
            System.out.println(implementationJson);
            response.setBody(new JSONObject().put("implementation added", implementationJson).toString());
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }

}
