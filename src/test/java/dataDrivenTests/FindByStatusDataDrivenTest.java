package dataDrivenTests;

import utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

import java.util.Map;

public class FindByStatusDataDrivenTest extends BaseTest {

	@DataProvider(name = "findByStatusData")
	public Object[][] findByStatusData() throws Exception {
		return TestDataReader.getTestDataByType("DataDrivenGETbystatus");
	}

	@Test(dataProvider = "findByStatusData", description = "DDTC10-DDTC12 Data Driven Get by status Test Cases")
	public void testFindByStatus(Map<String, String> data) {
		String status = data.get("Status");
		int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
		Response resp = PetEndpoints.findPetsByStatus(status);
		Assert.assertEquals(resp.getStatusCode(), expected);
		if (expected == resp.getStatusCode()) {
			resp.then().body("status", everyItem(equalTo(status)));
		}
	}
}
