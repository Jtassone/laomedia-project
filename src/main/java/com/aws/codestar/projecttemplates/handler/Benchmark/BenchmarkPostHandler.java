package com.aws.codestar.projecttemplates.handler.Benchmark;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.aws.codestar.projecttemplates.handler.Implementation.Implementation;
import com.aws.codestar.projecttemplates.handler.Implementation.ImplementationService;
import com.aws.codestar.projecttemplates.handler.User.UserEvent;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.aws.codestar.projecttemplates.utils.S3Client;
import com.aws.codestar.projecttemplates.utils.UserEventService;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class BenchmarkPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Connection sqlConnection;
    Gson gson;

    public BenchmarkPostHandler() {
        this.sqlConnection = RDSClient.getRemoteConnection();
        this.gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        try {
            JSONObject eventBody = new JSONObject(event.getBody());
            String date = eventBody.getString("date");
            String instanceId = eventBody.getString("instanceId");
            String implementationId = eventBody.getString("implementationId");
            String core = eventBody.getString("core");
            String cpu = eventBody.getString("cpu");
            String l1 = eventBody.getString("l1");
            String l2 = eventBody.getString("l2");
            String l3 = eventBody.getString("l3");
            String numberThreads = eventBody.getString("numberThreads");
            String ram = eventBody.getString("ram");
            MachineConfig machineConfig = new MachineConfig(UUID.randomUUID(), core, cpu, l1, l2, l3, numberThreads, ram);
            Benchmark benchmark = new Benchmark(UUID.randomUUID(), Date.valueOf(date), machineConfig.id, UUID.fromString(instanceId), UUID.fromString(implementationId));
            BenchmarkService.saveMachineConfig(sqlConnection, machineConfig);
            BenchmarkService.saveBenchmark(sqlConnection, benchmark);
            String userName = event.getQueryStringParameters().get("userName");
            String eventDetails = "benchmark with the id " + benchmark.id;
            UserEvent userEvent = new UserEvent(UUID.randomUUID(), userName, "CREATED", eventDetails);
            UserEventService.logUserEvent(sqlConnection, userEvent);
            response.setBody(gson.toJson(benchmark));
            response.setStatusCode(200);
        } catch (SQLException e) {
            System.out.println(e);
            response.setStatusCode(500);
        }
        System.out.println("The response is" + response);
        return response;
    }

}
