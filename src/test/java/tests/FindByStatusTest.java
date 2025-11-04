package tests;

import  utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;
import static org.hamcrest.Matchers.*;

import java.util.Map;


public class FindByStatusTest extends BaseTest {

   
    
    
    
    //find by available Test casses TC18
    @DataProvider(name = "findByAvailableStatus")
    public Object[][] findByAvailableStatus() throws Exception {
        return TestDataReader.getTestDataByType("ByAvailable");
    }

    // TC18: Find pets with status=available
    @Test(dataProvider = "findByAvailableStatus", description = "TC18 Find by Available status Test Case")
    public void getByAvailableStatus(Map<String,String> data) {
        String status = data.get("Status");
        int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.findPetsByStatus(status);
        Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("status", everyItem(equalTo(status)));
    }
    
    
    
    
    
    //find by unknown Test casses TC19
    @DataProvider(name = "findByUnkonwntatus")
    public Object[][] findByUnkonwntatus() throws Exception {
        return TestDataReader.getTestDataByType("findByUnkonwntatus");
    }

    // TC18: Find pets with status=available
    @Test(dataProvider = "findByUnkonwntatus", description = "TC19 Find by unkonwn status Test Case")
    public void getByUnknowStatus(Map<String,String> data) {
        String status = data.get("Status");
        int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.findPetsByStatus(status);
        Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("", hasSize(0));
    }
    
    
    
    
    
    //find by Blank status Test casses TC20
    @DataProvider(name = "findByblankStatus")
    public Object[][] findByblankStatus() throws Exception {
        return TestDataReader.getTestDataByType("findByblankStatus");
    }

    // TC20: Find pets with status=available
    @Test(dataProvider = "findByblankStatus", description = "TC20 Find by blank status Test Case")
    public void getByBlankStatus(Map<String,String> data) {
        String status = data.get("Status");
        int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.findPetsByStatus(status);
        Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("status", everyItem(equalTo("")));
    }
    
    
    //find by Blank sold Test casses TC21
    @DataProvider(name = "findBysoldStatus")
    public Object[][] findBysoldStatus() throws Exception {
        return TestDataReader.getTestDataByType("BySold");
    }

    // TC21: Find pets with status=sold
    @Test(dataProvider = "findBysoldStatus", description = "TC21 Find by sold status Test Case")
    public void getBysoldStatus(Map<String,String> data) {
        String status = data.get("Status");
        int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.findPetsByStatus(status);
        Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("status", everyItem(equalTo(status)));
    }
    
    
    
    
    
    
    
}
