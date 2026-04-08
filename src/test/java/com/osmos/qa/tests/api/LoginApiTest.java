package com.osmos.qa.tests.api;

import com.osmos.qa.config.ConfigManager;
import com.osmos.qa.base.BaseApiTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.matchesRegex;

public class LoginApiTest extends BaseApiTest {

    @Test(description = "Successful login with valid credentials")
    public void testLoginSuccess() {
        given().spec(baseSpec).log().all()
                .body(loginBody(ConfigManager.get("default.email"), ConfigManager.get("default.password")))
                .post("/login")
                .then().log().all().spec(successSpec)
                .body("success", equalTo(true))
                .body("token", matchesRegex("^Bearer_[A-Z0-9_]+$"))
                .body("email", equalTo(ConfigManager.get("default.email")));
    }

    @Test(description = "Login fails with invalid credentials")
    public void testLoginInvalidCredentials() {
        given().spec(baseSpec)
                .body(loginBody("wrong@company.com", "WrongPass"))
                .post("/login")
                .then().spec(unauthorizedSpec)
                .body("success", equalTo(false))
                .body("error", equalTo("Invalid credentials"));
    }

    @Test(description = "Login fails with empty email")
    public void testLoginEmptyEmail() {
        given().spec(baseSpec)
                .body(loginBody("", "Admin@123"))
                .post("/login")
                .then().spec(badRequestSpec)
                .body("success", equalTo(false))
                .body("error", equalTo("Email and password are required"));
    }

    @Test(description = "Login fails with empty password")
    public void testLoginEmptyPassword() {
        given().spec(baseSpec)
                .body(loginBody("admin@company.com", ""))
                .post("/login")
                .then()
                .body("success", equalTo(false))
                .body("error", equalTo("Email and password are required"));
    }
}
