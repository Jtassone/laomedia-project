package com.aws.codestar.projecttemplates.handler.Tests;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.Classification.Classification;
import com.aws.codestar.projecttemplates.handler.Classification.ClassificationPostHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class ClassificationPostTests {


    private RDSClient mockRDSService;
    private ClassificationPostHandler classificationPostHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        classificationPostHandler = new ClassificationPostHandler();
        context = new TestContext();
        // 1. Arrange
        Classification classification = new Classification(UUID.randomUUID(), "test classification 1", UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bdb"));


        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"name\":\"test classification 1\", \"parentClassificationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.classificationPostHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert
        Classification classificationResponse = gson.fromJson(response.getBody(), Classification.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(classification.name, classificationResponse.name);
    }

}