package com.aws.codestar.projecttemplates.handler.Tests;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.Benchmark.Benchmark;
import com.aws.codestar.projecttemplates.handler.Benchmark.BenchmarkPostHandler;
import com.aws.codestar.projecttemplates.handler.Benchmark.MachineConfig;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class BenchmarkPostTests {


    private RDSClient mockRDSService;
    private BenchmarkPostHandler benchmarkPostHandler;
    private TestContext context;



    @Test
    public void validRequest() throws SQLException {
        benchmarkPostHandler = new BenchmarkPostHandler();
        context = new TestContext();
        // 1. Arrange
        Date date = new Date();
        MachineConfig machineConfig = new MachineConfig(UUID.randomUUID(), "1", "2", "3", "4", "5", "6", "7");
        Benchmark benchmark = new Benchmark(UUID.randomUUID(), date, UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bd1"), UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bd2"), UUID.fromString("ee2eb871-36a8-484a-bba4-7c3ebd714bd3"));


        // Fake AWS Lambda request from API Gateway
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("{\"date\":\"2021-12-15\", " +
                        "\"instanceId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bd2\", \"core\": \"1\", \"cpu\": \"2\", \"l1\": \"3\",\"l2\": \"2\",\"l3\": \"3\",\"numberThreads\": \"4\", \"ram\": \"5\"," +
                        "\"implementationId\": \"ee2eb871-36a8-484a-bba4-7c3ebd714bd3\"}")
                .withQueryStringParameters(Map.of("userName", "testUser"));


        // 2. Act
        APIGatewayProxyResponseEvent response = this.benchmarkPostHandler.handleRequest(
                request,
                context
        );

        Gson gson = new Gson();
        // 3. Assert
        Benchmark benchmarkResponse = gson.fromJson(response.getBody(), Benchmark.class);
        assertEquals(200, (int) response.getStatusCode());
        assertEquals(benchmark.date, benchmarkResponse.date);

    }

}