package com.aws.codestar.projecttemplates.handler.Classification;

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

public class ClassificationPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Connection sqlConnection;
    Gson gson;

    public ClassificationPostHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        try {
            JSONObject eventBody = new JSONObject(event.getBody());
            String name = eventBody.getString("name");
            String subClassificationId = eventBody.getString("sub_classification_id");
            Classification classification;
            if (subClassificationId == null) {
                classification = new Classification(name);
            } else {
                classification = new Classification(name, UUID.fromString(subClassificationId));
            }
            Classification postedClassification = ClassificationService.postClassification(sqlConnection, classification);
            response.setBody(new JSONObject().put("classification added", gson.toJson(postedClassification)).toString());
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }

}
