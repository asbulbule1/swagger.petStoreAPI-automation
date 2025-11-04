package tests;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utils.BaseTest;

public class SchemaValidations extends BaseTest {
	
	@Test
	public void schemavaliationGetResponse() {
		
		Response res=endpoints.PetEndpoints.getPetById(9404);
		res.then()
		.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/GetResponseSchema.json"));
		
	}

}
