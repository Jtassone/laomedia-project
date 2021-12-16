package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.lambda.runtime.tests.EventLoader;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.Classification.ClassificationHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.UUIDUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class AlgorithmPostTests {


    private RDSClient mockRDSService;
    private AlgorithmPostHandler algorithmPostHandler;
    private AlgorithmHandler algorithmHandler;
    private AlgorithmDeleteHandler algorithmDeleteHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        algorithmPostHandler = new AlgorithmPostHandler();
        context = new TestContext();
        // 1. Arrange
        Algorithm algorithm = new Algorithm(UUID.randomUUID(), "test algorithm 1", "this is a test algorithm", UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bdb"));


        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"name\":\"test algorithm 1\", \"classificationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\", \"algorithmDetails\": \"this is a test algorithm\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.algorithmPostHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert
        Algorithm algorithmResponse = gson.fromJson(response.getBody(), Algorithm.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(algorithm.name, algorithmResponse.name);
        assertEquals(algorithm.algorithmDetails, algorithmResponse.algorithmDetails);
    }
    @Test
    public void validRequest2() throws SQLException {
        algorithmDeleteHandler = new AlgorithmDeleteHandler();
        context = new TestContext();
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"UUID\":\"ee2e3471-36a8-484a-bba4-7c3ebd714bdb\", \"name\":\"test algorithm 1\", \"classificationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\", \"algorithmDetails\": \"this is a test algorithm\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));
        APIGatewayProxyResponseEvent response = this.algorithmPostHandler.handleRequest(
                request,
                context
        );
        Gson gson = new Gson();
        String IdResponse = gson.fromJson(response.getBody(), String.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(IdResponse, "ee2e3471-36a8-484a-bba4-7c3ebd714bdb");

    }
    @Test
    public void validRequest3() throws SQLException {
        algorithmHandler = new AlgorithmHandler();
        context = new TestContext();
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"UUID\":\"ee2e3471-36a8-484a-bba4-7c3ebd714bdb\", \"name\":\"test algorithm 1\", \"classificationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\", \"algorithmDetails\": \"this is a test algorithm\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));
        APIGatewayProxyResponseEvent response = this.algorithmPostHandler.handleRequest(
                request,
                context
        );
        Gson gson = new Gson();
        String IdResponse = gson.fromJson(response.getBody(), String.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(IdResponse, "ee2e3471-36a8-484a-bba4-7c3ebd714bdb");

    }
}