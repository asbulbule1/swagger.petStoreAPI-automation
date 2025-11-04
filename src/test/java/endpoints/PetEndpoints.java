package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.Pet;

import static io.restassured.RestAssured.given;

public class PetEndpoints {

    public static Response createPet(Pet pet) {
    	Response res= given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet");
        
        return res;
    }

    public static Response getPetById(Object petId) {
    	Response res= given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    	 return res;
    }

    public static Response updatePet(Pet pet) {
    	Response res= given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .put("/pet");
        return res;
    }

    public static Response deletePet(Object petId) {
    	Response res= given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}");
    	 return res;
    }

    public static Response findPetsByStatus(String status) {
    	Response res= given()
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus");
    	 return res;
    }
}
