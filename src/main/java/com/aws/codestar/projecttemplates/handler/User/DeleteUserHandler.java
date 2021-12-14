package com.aws.codestar.projecttemplates.handler.User;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class DeleteUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Connection sqlConnection;
    Gson gson;
    CognitoIdentityProviderClient cognitoClient;

    public DeleteUserHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
        cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent lambdaResponse = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            String userName = event.getPathParameters().get("userName");
            AdminDeleteUserRequest deleteUserRequest = AdminDeleteUserRequest.builder().userPoolId("us-east-1_lmPiyyHcZ").username(userName).build();
            AdminDeleteUserResponse deleteUserResponse = cognitoClient.adminDeleteUser(deleteUserRequest);
            cognitoClient.close();
            lambdaResponse.setBody(gson.toJson("success"));
            lambdaResponse.setStatusCode(200);
        } catch (Exception e) {
            lambdaResponse.setBody(e.toString());
            lambdaResponse.setStatusCode(500);
            e.printStackTrace();
        }
        System.out.println("The response is" + lambdaResponse);
        return lambdaResponse;
    }
}
