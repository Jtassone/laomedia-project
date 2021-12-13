package com.aws.codestar.projecttemplates.handler.Algorithm;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class AlgorithmDeleteHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Connection sqlConnection;
    Gson gson;
    //
    public AlgorithmDeleteHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            String id = event.getPathParameters().get("path").split("/")[2];
            System.out.println(id);
            AlgorithmService.deleteAlgorithms(sqlConnection, id);
            response.setBody(gson.toJson(id));
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }
}
