package com.aws.codestar.projecttemplates.handler.Instance;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmService;
import com.aws.codestar.projecttemplates.handler.Classification.Classification;
import com.aws.codestar.projecttemplates.handler.Classification.ClassificationService;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.S3Client;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class InstanceHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    S3Client s3Client;
    Connection sqlConnection;
    Gson gson;

    public InstanceHandler() {
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
            List<Instance> instancesList = InstanceService.getAllInstances(sqlConnection, s3Client);
            response.setBody(gson.toJson(instancesList));
            response.setStatusCode(200);
        } catch (SQLException | IOException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }
}