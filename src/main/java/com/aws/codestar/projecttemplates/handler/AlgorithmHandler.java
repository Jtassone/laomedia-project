package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AlgorithmHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Connection getRemoteConnection() {
            try {
                String jdbcUrl = "jdbc:mysql://laomedia.cffhqwuildxe.us-east-1.rds.amazonaws.com:3306/laoData?user=admin&password=Thisisatestdatabase";
                return DriverManager.getConnection(jdbcUrl);
            }
            catch (SQLException e) {
                System.out.println(e);
                return null;
            }
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        Connection sqlConnection = getRemoteConnection();
        try {
            ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from algorithms");
            response.setBody(sqlResponse.toString());
            response.setStatusCode(200);
        } catch (Exception e) {
            System.out.println("In the catch of the try catch ");
            System.out.println(e);
            response.setStatusCode(500);
        }
        headers.put("Content-Type", "application/json");
        System.out.println(event);
        return response;
    }
}
