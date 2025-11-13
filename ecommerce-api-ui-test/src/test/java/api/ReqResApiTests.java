package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReqResApiTest {

    @Test(priority = 1)
    public void createUserTest() {
        RestAssured.baseURI = "https://reqres.in";

        String requestBody = "{\n" +
                "  \"name\": \"Ayan Kumar Dash\",\n" +
                "  \"job\": \"QA Automation Engineer\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("Ayan Kumar Dash"))
                .body("job", equalTo("QA Automation Engineer"))
                .extract().response();

        String id = response.jsonPath().getString("id");
        String createdAt = response.jsonPath().getString("createdAt");

        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertTrue(createdAt.contains("202"), "createdAt should contain a timestamp");

        System.out.println("✅ User Created: " + response.asPrettyString());
    }

    @Test(priority = 2)
    public void getUserTest() {
        RestAssured.baseURI = "https://reqres.in";

        Response response = given()
                .when()
                .get("/api/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .extract().response();

        System.out.println("✅ Retrieved User #2: " + response.asPrettyString());
    }
}
