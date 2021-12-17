package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Benchmark.BenchmarkDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Implementation.ImplementationDeleteHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BenchmarkDeleteTests {

    private BenchmarkDeleteHandler benchmarkDeleteHandler;
    private TestContext context;

    @Test
    public void validRequest2() throws SQLException {
        benchmarkDeleteHandler = new BenchmarkDeleteHandler();
        context = new TestContext();
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Map.of("UUID", "ee2eb871-36a8-484a-bba4-7c3ebd714bdb"))
                .withQueryStringParameters(Map.of("userName", "testUser"));

        APIGatewayProxyResponseEvent response = this.benchmarkDeleteHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        String IdResponse = gson.fromJson(response.getBody(), String.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(IdResponse, "ee2eb871-36a8-484a-bba4-7c3ebd714bdb");
    }

}
