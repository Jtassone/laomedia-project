package com.aws.codestar.projecttemplates.handler.Algorithm;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class AlgorithPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {



    Connection sqlConnection;
    Gson gson;
//
    public AlgorithPostHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }



   @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            Object obj = new JSONParser().parse(new FileReader(event.getBody()));
            JSONObject jo = (JSONObject) obj;
            String name = (String) jo.get("name");
            String implementation = (String) jo.get("implementation");
            Algorithm algo = new Algorithm(name, implementation);
            AlgorithmService.postAlgorithms(sqlConnection, algo);
            response.setBody(new JSONObject().put("algorithm added", gson.toJson(algo)).toString());
            response.setStatusCode(200);
        } catch (SQLException | IOException | ParseException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }




}


