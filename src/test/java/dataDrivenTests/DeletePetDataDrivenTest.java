package dataDrivenTests;

import utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;

import java.util.Map;


public class DeletePetDataDrivenTest extends BaseTest {

    @DataProvider(name = "deletePetData")
    public Object[][] deletePetData() throws Exception {
        return TestDataReader.getTestDataByType("DataDrivenDelete");
    }

    @Test(dataProvider = "deletePetData", description = "DDTC13-DDTC15 Data Driven Delete Test cases")
    public void testDeletePet(Map<String,String> data) {
        int  id =Integer.parseInt(data.get("ID"));
        int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.deletePet(id);
        int status = resp.getStatusCode();
        Assert.assertTrue(status == expected || status == 404);
    }
}
