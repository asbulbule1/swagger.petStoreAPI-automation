package petLifeCycyle;

import java.util.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import endpoints.PetEndpoints;
import io.restassured.response.Response;
import pojo.Pet;
import utils.BaseTest;
import utils.TestDataReader;
import static org.hamcrest.Matchers.*;

public class End2EndLifeCycleTest extends BaseTest {

    public static List<Integer> createdPetIds = new ArrayList<>();

    @DataProvider(name = "E2ELifecycle")
    public Object[][] createPetData() throws Exception {
        return TestDataReader.getTestDataByType("E2ELifeCycle");
    }

    @Test(dataProvider = "E2ELifecycle", description = "End-to-End Lifecycle: Create → Get → Update → Delete → Verify Deleted")
    public void e2eLifecycleTest(Map<String, String> data) {
    	
    	
    	System.out.println(data);

        // --- Test Data Setup ---
        int id = Integer.parseInt(data.get("ID"));
        String name = data.get("Name");
        String status = data.get("Status");
        int catId = Integer.parseInt(data.get("CatID"));
        String catName = data.get("CatName");
        int tagId = Integer.parseInt(data.get("TagID"));
        String tagName = data.get("TagName");

        // --- Create Pet Payload ---
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);
        pet.setPhotoUrls(Collections.singletonList("https://pet.url/" + name + ".jpg"));

        Pet.Category category = new Pet.Category();
        category.id = catId;
        category.name = catName;
        pet.setCategory(category);

        Pet.Tag tag1 = new Pet.Tag();
        tag1.id = tagId;
        tag1.name = tagName;
        pet.setTags(Collections.singletonList(tag1));

        // --- Create Pet ---
        Response resp = PetEndpoints.createPet(pet);
        resp.then()
            .statusCode(200)
            .body("id", equalTo(id));
        int createdID = resp.jsonPath().getInt("id");
        createdPetIds.add(createdID);

        // --- Retrieve Pet ---
        Response resp1 = PetEndpoints.getPetById(createdID);
        resp1.then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("name", equalTo(name))
            .body("status", equalTo(status));

        // --- Update Pet ---
        String newName = name + "_updated";
        pet.setName(newName);
        Response resp2 = PetEndpoints.updatePet(pet);
        resp2.then()
            .statusCode(200)
            .body("id", equalTo(createdID))
            .body("name", equalTo(newName));

        // --- Verify Updated Pet ---
        Response resp3 = PetEndpoints.getPetById(createdID);
        resp3.then()
            .statusCode(200)
            .body("name", equalTo(newName));

        // --- Delete Pet ---
        Response resp4 = PetEndpoints.deletePet(createdID);
        int deleteStatusCode = resp4.getStatusCode();
        Assert.assertTrue(deleteStatusCode == 200);

        // --- Verify Deletion ---
        Response resp5 = PetEndpoints.getPetById(createdID);
        resp5.then()
            .statusCode(404)
            .body("message", equalTo("Pet not found"));
    }
}
