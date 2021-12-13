package java.com.aws.codestar.projecttemplates.handler;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;


public class HandlerTests {

    private Context createContext() {
        TestContext tc = new TestContext();
        tc.setFunctionName("Your Function Name");

        return tc;
    }


    @Test
    public void testAlgosDelete(){
        APIGatewayProxyResponseEvent response_delete = new APIGatewayProxyResponseEvent();
        APIGatewayProxyRequestEvent request_delete= new APIGatewayProxyRequestEvent();
        Context ct= createContext();

        com.aws.codestar.projecttemplates.handler.Algorithm.AlgorithmDeleteHandler adh= new AlgorithmDeleteHandler();
        adh.handleRequest(request_delete,ct); // what should the event be

        assertEquals(response_delete.statusCode,200);

    }


    @Test public void testAlgosPost(){

    }

    @Test public void testAlgosGet(){

    }

    @Test public void testClassifictionsGet(){

    }

    @Test public void testPostClassifications(){
        
    }


}
