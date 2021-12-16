package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.*;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class ReclassifyAlgorithmTests {


    private RDSClient mockRDSService;
    private AlgorithmReclassifyHandler algorithmReclassifyHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        algorithmReclassifyHandler = new AlgorithmReclassifyHandler();
        context = new TestContext();
        // 1. Arrange
        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"algorithmId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\", \"classificationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.algorithmReclassifyHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert

        assertEquals(200, (int) response.getStatusCode());

    }

}