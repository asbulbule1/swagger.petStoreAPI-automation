package dataDrivenTests;

import  utils.BaseTest;
import static org.hamcrest.Matchers.*;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.TestDataReader;

import java.util.Map;

/*
Data Driven tests
*/
public class GetPetDataDrivenTest extends BaseTest {

	@DataProvider(name = "getPetData")
	public Object[][] getPetData() throws Exception {
		return TestDataReader.getTestDataByType("DataDrivenGETbyID");
	}

	//Data Driver Test requires type=DataDrivenGETbyID
	@Test(dataProvider = "getPetData", description = "DDTC7-DDTC9 DataDriven Get By ID")
	public void testGetPet(Map<String, String> data) {
		int id = Integer.parseInt(data.get("ID"));
		int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
		Response resp = PetEndpoints.getPetById(id);
		Assert.assertTrue(resp.getStatusCode()==expected || resp.getStatusCode()==404);
		if (expected==resp.getStatusCode()) {
			resp.then().body("id",equalTo( id));
		}
	}
}
