package tests;

import  utils.BaseTest;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import pojo.Pet;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataReader;
import static org.hamcrest.Matchers.*;


import java.util.Map;

/*
Tests with various input ID inputs
*/
public class GetPetTest extends BaseTest {

  
	
	  //get Valid pet TC06
    @DataProvider(name = "validPet")
    public Object[][] getValidPet() throws Exception {
        return TestDataReader.getTestDataByType("GetExisting");
    }
    
    @Test(dataProvider = "validPet",description = "Get exting Pet TC06")
    public void getValidPet(Map<String,String> data) {
    	int id = Integer.parseInt( data.get("ID"));
    	Pet pet=new Pet();
    	pet.setId(id);
    	
    	//explicitly adding pet to Avoid 404
    	PetEndpoints.createPet(pet);
    	int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.getPetById(id);
        Assert.assertEquals(resp.getStatusCode(), expected);
        resp.then().body("id", equalTo(id));
    	
    } 
    
    // get Non existing Pet TC 07
    @DataProvider(name = "nonexting")
    public Object[][] getNonexistingPet() throws Exception {
        return TestDataReader.getTestDataByType("GetNonExisting");
    }
    
    @Test(dataProvider = "nonexting",description = "Get  non exting Pet TC07")
    public void nonexistingPet(Map<String,String> data) {
    	 int id = Integer.parseInt(data.get("ID"));
    	//explicitly deletting
    	endpoints.PetEndpoints.deletePet(id);
    	
    	 int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.getPetById(id);
        Assert.assertEquals(resp.getStatusCode(), expected);
        resp.then().body("message", equalTo("Pet not found"));
        resp.then().body("type", equalTo("error"));
    } 
    
    
    
    // get inavlidID Pet TC08
    @DataProvider(name = "invalidPetID")
    public Object[][] getInvalidPet() throws Exception {
        return TestDataReader.getTestDataByType("invalidPetID");
    }
    
    @Test(dataProvider = "invalidPetID",description = "Get invalid ID Pet TC08")
    public void getNonInvalidPet(Map<String,String> data) {
    	String idStr = data.get("ID");
    	 int expected = Integer.parseInt(data.get("ExpectedStatusCode"));
        Response resp = PetEndpoints.getPetById(idStr);
        Assert.assertEquals(resp.getStatusCode(), expected);
       resp.then().body("message", containsString("NumberFormatException"));
      resp.then().body("type", equalTo("unknown"));
    	
    } 
    
    

    
    // get deleted Pet TC09
    @DataProvider(name = "DeletedPet")
    public Object[][] getDeletedPet() throws Exception {
        return TestDataReader.getTestDataByType("DelettedPet");
    }

    @Test(dataProvider = "DeletedPet",description = "Get deletted Pet TC09")
    public void getDeletedPetTest(Map<String,String> data) {
    	  	
    	 int petId = Integer.parseInt( data.get("ID"));
    	 //explicitely deletting the Pet
    	 PetEndpoints.deletePet(petId);
    	 
    	 
        Response resp = PetEndpoints.getPetById(petId);
        Assert.assertEquals(resp.getStatusCode(), 404);
        resp.then().body("message", equalTo("Pet not found"));
    }
}
