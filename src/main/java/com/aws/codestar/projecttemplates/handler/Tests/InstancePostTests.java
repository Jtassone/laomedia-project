package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.Instance.Instance;
import com.aws.codestar.projecttemplates.handler.Instance.InstancePostHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class InstancePostTests {


    private RDSClient mockRDSService;
    private InstancePostHandler instancePostHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        instancePostHandler = new InstancePostHandler();
        context = new TestContext();
        // 1. Arrange
        Instance instance = new Instance(UUID.randomUUID(), "test instance 1", UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bdb"), UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bd2"));


        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"name\":\"test instance 1\", \"algorithmId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\", \"implementationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bd2\", \"instanceFileString\": \"VGhpcyBpcyBhIHRlc3QgYmFzZSAgNjQgZW5jb2RlZCBzdHJpbmcuIA==\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.instancePostHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert
        Instance instanceResponse = gson.fromJson(response.getBody(), Instance.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(instance.name, instanceResponse.name);
        assertEquals(instance.algorithmId, instanceResponse.algorithmId);
    }

}