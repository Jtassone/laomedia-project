package com.aws.codestar.projecttemplates.handler.Algorithm;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class AlgorithmPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Connection sqlConnection;
    Gson gson;
//
    public AlgorithmPostHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }

   @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            JSONObject eventBody = new JSONObject(gson.toJson(event.getBody()));
            String name = (String) eventBody.get("name");
            String algorithmDetails = (String) eventBody.get("algorithmDetails");
            Algorithm algo = new Algorithm(name, algorithmDetails);
            AlgorithmService.postAlgorithms(sqlConnection, algo);
            response.setBody(new JSONObject().put("algorithm added", gson.toJson(algo)).toString());
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }

}
