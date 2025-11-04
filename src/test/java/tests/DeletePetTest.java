package tests;

import  utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import pojo.Pet;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;


public class DeletePetTest extends BaseTest {

    @DataProvider(name = "deleteExistingPet")
    public Object[][] deletePetData() throws Exception {
        return TestDataReader.getTestDataByType("DeleteExisting");
    }
//TC 14 Delete existing Pet
    @Test(dataProvider = "deleteExistingPet", description = "Deletting Existing PetTC14 ")
    public void testDeleteExistingPet(Map<String,String> data) {
    	//explicitly adding pet before deletting
    	
    	int id = Integer.parseInt(data.get("ID"));
		Pet pet = new Pet();
		pet.setId(id);
		PetEndpoints.createPet(pet);
		
		
		
        Response resp = PetEndpoints.deletePet(id);
        int status = resp.getStatusCode();
        Assert.assertTrue(status == 200 || status == 404);
        
        // creating the pet again to avoid failure in next run
        PetEndpoints.createPet(pet);
    }
    
    
    
 // TC15 :  Delete Already Deletted pet
    
    
    @DataProvider(name = "deleteAlreadyDeletted")
    public Object[][] deleteAlreadyDeletted() throws Exception {
        return TestDataReader.getTestDataByType("AlreadyDeletted");
    }
    
    @Test(dataProvider = "deleteAlreadyDeletted", description = "Deletting already Delettted Pet TC15 ")
    public void testdeleteAlreadyDeletted(Map<String,String> data) {
    	int id = Integer.parseInt(data.get("ID"));
    	//Explicitly deletting pet 
    	 PetEndpoints.deletePet(id);
		
        Response resp = PetEndpoints.deletePet(id);
        int status = resp.getStatusCode();
        Assert.assertTrue(status == 200 || status == 404);
        
        // creating the pet again to avoid failure in next run
        Pet pet = new Pet();
		pet.setId(id);
		PetEndpoints.createPet(pet);
    }
    
    
    
    //TC16 : Delete Non-Existing Pet
    
    @DataProvider(name = "deletenonExistingPet")
    public Object[][] deletenonExistingPet() throws Exception {
        return TestDataReader.getTestDataByType("DeleteNonExisting");
    }
    
    @Test(dataProvider = "deletenonExistingPet", description = "Deletting non existing Pet TC16 ")
    public void testdeletenonExistingPet(Map<String,String> data) {
    	int id = Integer.parseInt(data.get("ID"));
    	//Explicitly deletting pet 
    	 PetEndpoints.deletePet(id);
    	 
		
        Response resp = PetEndpoints.deletePet(id);
        int status = resp.getStatusCode();
        Assert.assertTrue(status == 200 || status == 404);
    }
    
    
    
    // TC17: Delete invalid Pet
    @DataProvider(name = "deleteInvalidPetData")
    public Object[][] deleteInvalidPetData() throws Exception {
        return TestDataReader.getTestDataByType("DeleteInvalidPet");
    }

   
    @Test(dataProvider = "deleteInvalidPetData", description = "TC17 Delete invalid ID pet")
    public void testdeleteInvalidPetData(Map<String,String> data) {
    	String id = data.get("ID");
        Response resp = PetEndpoints.deletePet(id);
        int status = resp.getStatusCode();
        Assert.assertTrue(status== 404);
        resp.then().body("message", containsString("NumberFormatException"));
        resp.then().body("type", equalTo("unknown"));
    }

}
