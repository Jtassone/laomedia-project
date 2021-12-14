package com.aws.codestar.projecttemplates.handler.Implementation;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.S3Client;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ImplementationHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    S3Client s3Client;
    Connection sqlConnection;
    Gson gson;

    public ImplementationHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
        this.s3Client = new S3Client();
    }

    @Override
    //TODO what do we do if we have an implementatopm om tje db but it isn't in s3
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            List<Implementation> implementationList = ImplementationService.getAllImplementations(sqlConnection, s3Client);
            response.setBody(gson.toJson(implementationList));
            response.setStatusCode(200);
        } catch (SQLException | IOException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }
}
