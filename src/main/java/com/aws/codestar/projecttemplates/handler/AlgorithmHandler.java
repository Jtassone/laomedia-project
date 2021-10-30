package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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

    Connection sqlConnection;

    public AlgorithmHandler() {
        this.sqlConnection = getRemoteConnection();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
         String name = null;
         try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from algorithms")) {
             while (sqlResponse.next()) {
                name = sqlResponse.getString("name");
             }
             String jsonResponse = String.format("{ name: %s }", name);
             response.setBody(jsonResponse);
             response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        headers.put("Content-Type", "application/json");
        System.out.println("The response is" + response);
        return response;
    }
}
