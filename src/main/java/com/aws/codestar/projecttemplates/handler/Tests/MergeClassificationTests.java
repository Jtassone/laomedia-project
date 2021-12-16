package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.Classification.MergeClassificationHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class MergeClassificationTests {


    private RDSClient mockRDSService;
    private MergeClassificationHandler mergeClassificationHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        mergeClassificationHandler = new MergeClassificationHandler();
        context = new TestContext();
        // 1. Arrange


        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"newClassificationName\":\"test classification 1\", \"classification1Id\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\", " +
                        "\"classification2Id\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bd2\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.mergeClassificationHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert

        assertEquals(200, (int) response.getStatusCode());

    }

}