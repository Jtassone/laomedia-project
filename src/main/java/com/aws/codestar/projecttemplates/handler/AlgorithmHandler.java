package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class AlgorithmHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Connection getRemoteConnection() {
        if (System.getenv("RDS_HOSTNAME") != null) {
            try {
                String jdbcUrl = "jdbc:mysql://laomedia.cffhqwuildxe.us-east-1.rds.amazonaws.com:3306/laoData?user=admin&password=Thisisatestdatabase";
                return DriverManager.getConnection(jdbcUrl);
            }
            catch (SQLException e) { System.out.println(e);}
        }
        return null;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        response.setStatusCode(200);
        Object eventBody = gson.toJson(event.getBody());
        System.out.println(eventBody);
        Algorithm testAlgo = new Algorithm("Test Algorithm");
        Connection sqlConnection = getRemoteConnection();
        try {
            sqlConnection.createStatement().execute("Select * from algorithm");
        } catch (Exception e) {
            System.out.println("In the catch of the try catch ");
            System.out.println(e);
        }
        headers.put("Content-Type", "application/json");
        System.out.println(event);
        return response;
    }
}
