package tests;

import  utils.BaseTest;
import static org.hamcrest.Matchers.*;
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
public class UpdatePetTest extends BaseTest {

	
	
	//update existing
    @DataProvider(name = "updatePetData")
    public Object[][] updatePetData() throws Exception {
        return TestDataReader.getTestDataByType("updateExisting");
    }

    // TC10: Update valid pet’s status
    @Test(dataProvider = "updatePetData", description = "TC10 update existing pet Data")
    public void testUpdatePet(Map<String,String> data) {  	
    	int id = Integer.parseInt(data.get("ID"));
		Pet pet = new Pet();
		pet.setId(id);
		//Explicitly creating pet before updating
		PetEndpoints.createPet(pet);
		
		
        String name = data.get("Name");
        String status = data.get("Status");
        int expected = Integer.parseInt(data.getOrDefault("ExpectedStatusCode","200"));
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://image.url/updated"+name+".jpg"));
		Response resp = PetEndpoints.updatePet(pet);
		resp.then().body("id", equalTo(id));
		Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("name", equalTo(name));
		resp.then().body("status", equalTo(status));
		
    }
    
    
    
    //update non existing
	
    @DataProvider(name = "updateNonexeistingPetData")
    public Object[][] updateNonexeistingPetData() throws Exception {
        return TestDataReader.getTestDataByType("PutNonExisting");
    }

    // TC11: Update non Existing Pet
    @Test(dataProvider = "updateNonexeistingPetData", description = "TC11 updating non existing Pet")
    public void testupdateNonexeistingPetData(Map<String,String> data) {  	
    	int id = Integer.parseInt(data.get("ID"));
    	//explicitly deletting 
    	endpoints.PetEndpoints.deletePet(id);
    	
        String name = data.get("Name");
        String status = data.get("Status");
        int expected = Integer.parseInt(data.getOrDefault("ExpectedStatusCode","200"));

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://image.url/updated"+name+".jpg"));
		Response resp = PetEndpoints.updatePet(pet);
		resp.then().body("id", equalTo(id));
		Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("name", equalTo(name));
		resp.then().body("status", equalTo(status));

    }
    
    
    
    
    //update with invalid json
    @DataProvider(name = "updatewithInvaliddatagPetData")
    public Object[][] updatewithInvaliddatagPetData() throws Exception {
        return TestDataReader.getTestDataByType("Putinvalidata");
    }

    // TC12: update with invalid Json
    @Test(dataProvider = "updatewithInvaliddatagPetData", description = "TC12 update with invalid Json")
    public void testupdatewithInvaliddatagPetData(Map<String,String> data) {  	
    	int id = Integer.parseInt(data.get("ID"));
        String name = data.get("Name");
        String status = data.get("Status");
        int expected = Integer.parseInt(data.getOrDefault("ExpectedStatusCode","200"));

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://image.url/updated"+name+".jpg"));
		Response resp = PetEndpoints.updatePet(pet);
		resp.then().body("id", equalTo(id));
		Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("name", equalTo(name));
		resp.then().body("status", equalTo(status));

    }
    
    //update missingID
    @DataProvider(name = "updatewithMissingIDPetData")
    public Object[][] updatewithMissingIDPetData() throws Exception {
        return TestDataReader.getTestDataByType("PutWithoutID");
    }

    // TC13:  Update without ID
    @Test(dataProvider = "updatewithMissingIDPetData", description = "TC13 Update without ID")
    public void testupdatewithMissingIDPetData(Map<String,String> data) {  	
        String name = data.get("Name");
        String status = data.get("Status");
        int expected = Integer.parseInt(data.getOrDefault("ExpectedStatusCode","200"));

        Pet pet = new Pet();
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://image.url/updated"+name+".jpg"));
		Response resp = PetEndpoints.updatePet(pet);
		resp.then().body("id", notNullValue());
		Assert.assertEquals(resp.getStatusCode(), expected);
		resp.then().body("name", equalTo(name));
		resp.then().body("status", equalTo(status));

    }
    
    
    
}
