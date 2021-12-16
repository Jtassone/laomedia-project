package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.Implementation.Implementation;
import com.aws.codestar.projecttemplates.handler.Implementation.ImplementationHandler;
import com.aws.codestar.projecttemplates.handler.Implementation.ImplementationPostHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class ImplementationPostTests {


    private RDSClient mockRDSService;
    private ImplementationPostHandler implementationPostHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        implementationPostHandler = new ImplementationPostHandler();
        context = new TestContext();
        // 1. Arrange
        Implementation implementation = new Implementation(UUID.randomUUID(), "test implementation 1", "this is a test implementation", UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bdb"));


        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"name\":\"test implementation 1\", \"implementationDetails\": \"this is a test implementation\"," +
                        "\"algorithmID\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bdb\" }")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.implementationPostHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert
        Implementation implementationResponse = gson.fromJson(response.getBody(), Implementation.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(implementation.name, implementationResponse.name);
        assertEquals(implementation.implementationDetails, implementationResponse.implementationDetails);
    }

}