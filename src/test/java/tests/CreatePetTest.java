package tests;
import  utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import pojo.Pet;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;
import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
public class CreatePetTest extends BaseTest {
	public static List<Integer> createdPetIds=new ArrayList<Integer>();;


	//Create a Pet with valid/invalid Data TC01,TC02,TC04,TC05
	
	@DataProvider(name = "CreateValidInvalid")
	public Object[][] createValidData() throws Exception {
		return TestDataReader.getTestDataByType("PostValidInvalid");
	}

	@Test(dataProvider = "CreateValidInvalid", description = "Create a Pet with valid/invalid Data TC01,TC02,TC04,TC05")
	public void testcreateValidData(Map<String, String> data) {
		int id = Integer.parseInt(data.get("ID"));
		String name = data.get("Name");
		String status = data.get("Status");
		int expected = Integer.parseInt(data.getOrDefault("ExpectedStatusCode", "200"));
		int catId = Integer.parseInt(data.get("CatID"));
		String catName = data.get("CatName");

		int tagId = Integer.parseInt(data.get("TagID"));
		String tagName = data.get("TagName");

		// adding pet data to pojo
		Pet pet = new Pet();
		pet.setId(id);
		pet.setName(name);
		pet.setStatus(status);
		pet.setPhotoUrls(Collections.singletonList("https://image.url/" + name + "+.jpg"));

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

		Response resp = PetEndpoints.createPet(pet);
		resp.then().body("id", equalTo(id));
		Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("status", equalTo(status));
		createdPetIds.add(id);

	}
	
	
	//create with duplicate ID 
	
	@DataProvider(name = "createexistingPetID")
	public Object[][] createexistingPetID() throws Exception {
		return TestDataReader.getTestDataByType("PostDuplicate");
	}

	// TC04 :  Post Duplicate Data
	@Test(dataProvider = "createexistingPetID", description = "TC04 Post Duplicate Data")
	public void testcreateexistingPetID(Map<String, String> data) {
		
		int id = Integer.parseInt(data.get("ID"));
		//Explicitly adding the Pet First
		Pet pet = new Pet();
		pet.setId(id);
		PetEndpoints.createPet(pet);
		
		
		String name = data.get("Name");
		String status = data.get("Status");
		int expected = Integer.parseInt(data.getOrDefault("ExpectedStatusCode", "200"));
		int catId = Integer.parseInt(data.get("CatID"));
		String catName = data.get("CatName");

		int tagId = Integer.parseInt(data.get("TagID"));
		String tagName = data.get("TagName");

		// adding pet data to pojo
		pet.setName(name);
		pet.setStatus(status);
		pet.setPhotoUrls(Collections.singletonList("https://image.url/" + name + "+.jpg"));

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

		//creating a pet with existing ID
		Response resp = PetEndpoints.createPet(pet);
		resp.then().body("id", equalTo(id));
		Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("status", equalTo(status));
		createdPetIds.add(id);

	}
	
	// cleaning the data after completing the test cases.
	@AfterClass
	public void flushingPet() {
	    for (int id : createdPetIds) {
	        PetEndpoints.deletePet(id);
	    }
	}
	


	}
	

