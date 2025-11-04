package dataDrivenTests;

import  utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import pojo.Pet;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;

import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.Map;

public class UpdatePetDataDrivenTest extends BaseTest {

    @DataProvider(name = "updatePetData")
    public Object[][] updatePetData() throws Exception {
        return TestDataReader.getTestDataByType("DataDrivenPut");
    }

    
    @Test(dataProvider = "updatePetData", description = "DDTC4-DDTC6 Data Driven Update Test Cases")
    public void testUpdatePet(Map<String,String> data) {
		int id = Integer.parseInt(data.get("ID"));	
        String name = data.get("Name");
        String status = data.get("Status");
        
    	int catId = Integer.parseInt(data.get("CatID"));
		String catName = data.get("CatName");

		int tagId = Integer.parseInt(data.get("TagID"));
		String tagName = data.get("TagName");
        int expected = Integer.parseInt(data.get("ExpectedStatusCode"));

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://image.url/"+name+"updated.jpg"));
        
        
    	// --- Set category ---
		Pet.Category category = new Pet.Category();
		category.id = catId;
		category.name = catName;
		pet.setCategory(category);

		// --- Set tags ---
		Pet.Tag tag1 = new Pet.Tag();
		tag1.id = tagId;
		tag1.name = tagName;
		pet.setTags(Collections.singletonList(tag1));

        Response resp = PetEndpoints.updatePet(pet);
        Assert.assertEquals(resp.getStatusCode(), expected);
        resp.then().body("name", equalTo(name));
		resp.then().body("id", equalTo(id));	
    }
}
