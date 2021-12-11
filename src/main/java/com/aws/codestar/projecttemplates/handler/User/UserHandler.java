package com.aws.codestar.projecttemplates.handler.User;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;

import com.google.gson.Gson;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

public class UserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Connection sqlConnection;
    Gson gson;
    CognitoIdentityProviderClient cognitoClient;

    public UserHandler() {
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
            List<UserType> userList = listAllUsers(cognitoClient, "us-east-1_lmPiyyHcZ");
            cognitoClient.close();
            lambdaResponse.setBody(gson.toJson(userList));
            lambdaResponse.setStatusCode(200);
        } catch (Exception e) {
            lambdaResponse.setBody(e.toString());
            lambdaResponse.setStatusCode(500);
            e.printStackTrace();
        }
        System.out.println("The response is" + lambdaResponse);
        return lambdaResponse;
    }

    public static List<UserType> listAllUsers(CognitoIdentityProviderClient cognitoClient, String userPoolId) {
        List<UserType> userList = null;
        try {
            // List all users
            ListUsersRequest usersRequest = ListUsersRequest.builder()
                    .userPoolId(userPoolId)
                    .build();
            ListUsersResponse response = cognitoClient.listUsers(usersRequest);
            response.users().forEach(user -> {
                        System.out.println("User " + user.username() + " Status " + user.userStatus() + " Created " + user.userCreateDate());
                    }
            );
            userList = response.users();
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return userList;
    }
}