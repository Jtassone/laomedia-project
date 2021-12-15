package com.aws.codestar.projecttemplates.handler.Tests;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.lambda.runtime.tests.EventLoader;
import com.aws.codestar.projecttemplates.handler.Classification.ClassificationHandler;
import org.testng.annotations.Test;
import static org.junit.Assert.assertTrue;
import java.io.IOException;

public class Tests {
    @Test
    public void testLoadEventBridgeEvent() throws IOException {
        // Given
        APIGatewayProxyRequestEvent event = EventLoader.loadEvent("my_event.json", APIGatewayProxyRequestEvent.class);
        ClassificationHandler handler = new ClassificationHandler();

        // When
        APIGatewayProxyResponseEvent response = handler.handleRequest(event, null);

        // Then
        assertTrue(2==2);
    }
}
