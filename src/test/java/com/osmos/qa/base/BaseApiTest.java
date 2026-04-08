package com.osmos.qa.base;

import com.osmos.qa.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    protected RequestSpecification baseSpec;
    protected RequestSpecification authSpec;
    protected ResponseSpecification successSpec;
    protected ResponseSpecification unauthorizedSpec;
    protected ResponseSpecification badRequestSpec;

    @BeforeClass
    public void setUp() {
        baseSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getApiBaseUrl())
                .setContentType(ContentType.JSON)
                .build();

        successSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        unauthorizedSpec = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();

        badRequestSpec=new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();

        // Authenticate and build auth spec
        String token = given().spec(baseSpec)
                .body(loginBody(ConfigManager.get("default.email"), ConfigManager.get("default.password")))
                .post("/login")
                .jsonPath().getString("token");

        authSpec = new RequestSpecBuilder()
                .addRequestSpecification(baseSpec)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    protected Map<String, String> loginBody(String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }
}
