package com.aws.codestar.projecttemplates.handler.Tests;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmHandler;
import com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmPostHandler;
import com.aws.codestar.projecttemplates.handler.User.UserEventHandler;
import com.aws.codestar.projecttemplates.utils.RDSClient;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UserEventGetTests {

    private RDSClient mockRDSService;
    private UserEventHandler userEventHandler;
    private TestContext context;

    @BeforeEach
    public void setup() {

    }


    @Test
    public void validRequest3() throws SQLException {
        userEventHandler = new UserEventHandler();
        context = new TestContext();
        Gson gson = new Gson();

        APIGatewayProxyRequestEvent getRequest = new APIGatewayProxyRequestEvent()
                .withPathParameters(Map.of("userName", "testUser"));

        APIGatewayProxyResponseEvent getResponse = this.userEventHandler.handleRequest(
                getRequest,
                context
        );


        assertEquals(200, (int) getResponse.getStatusCode());
    }

}
