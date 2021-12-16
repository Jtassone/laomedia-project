package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.User.DeleteUserHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UserDeleteTests {

    private RDSClient mockRDSService;
    private DeleteUserHandler deleteUserHandler;
    private TestContext context;

    @Test
    public void validRequest2() throws SQLException {
        deleteUserHandler = new DeleteUserHandler();
        context = new TestContext();
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Map.of("userName", "admin"))
                .withQueryStringParameters(Map.of("userName", "testUser"));

        APIGatewayProxyResponseEvent response = this.deleteUserHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        String response2 = gson.fromJson(response.getBody(), String.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(response2, "success");
    }

}
