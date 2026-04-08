package com.osmos.qa.tests.api;

import com.osmos.qa.models.Lead;
import com.osmos.qa.utils.TestDataProvider;
import com.osmos.qa.base.BaseApiTest;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LeadsApiTest extends BaseApiTest {

    // --- GET Leads ---

    @Test(description = "Get leads with valid token returns paginated list")
    public void testGetLeadsSuccess() {
        given().spec(authSpec).log().all()
                .get("/leads")
                .then().spec(successSpec).log().all()
                .body("success", equalTo(true))
                .body("leads", instanceOf(List.class))
                .body("total", greaterThan(0))
                .body("page", notNullValue())
                .body("pageSize", notNullValue());
    }

    @Test(description = "Fetch leads list with pagination - page 1 returns max pageSize leads")
    public void testGetLeadsPaginationPage1() {
        given().spec(authSpec)
                .queryParam("page", 1)
                .get("/leads")
                .then().spec(successSpec)
                .body("leads.size()", lessThanOrEqualTo(10))
                .body("page", equalTo(1))
                .body("pageSize", equalTo(10))
                .body("total", greaterThan(0));
    }

    @Test(description = "Fetch leads list with pagination - page 2 returns next set of leads")
    public void testGetLeadsPaginationPage2() {
        given().spec(authSpec)
                .queryParam("page", 2)
                .get("/leads")
                .then().spec(successSpec)
                .body("page", equalTo(2))
                .body("pageSize", equalTo(10));
    }

    @Test(description = "Get leads without auth header returns 401")
    public void testGetLeadsWithoutAuth() {
        given().spec(baseSpec)
                .get("/leads")
                .then().spec(unauthorizedSpec)
                .body("success", equalTo(false))
                .body("error", equalTo("Authorization header is required"));
    }

    @Test(description = "Get leads with invalid token returns 401")
    public void testGetLeadsInvalidToken() {
        given().spec(new RequestSpecBuilder()
                        .addRequestSpecification(baseSpec)
                        .addHeader("Authorization", "Bearer invalid_token_xyz")
                        .build())
                .get("/leads")
                .then().spec(unauthorizedSpec)
                .body("error", equalTo("Invalid or expired token"));
    }

    // --- CREATE Lead ---

    @Test(description = "Create lead with valid data returns success")
    public void testCreateLeadSuccess() {
        Lead lead = TestDataProvider.newLead();

        given().spec(authSpec)
                .body(leadToMap(lead))
                .post("/leads")
                .then().statusCode(201)
                .body("success", equalTo(true))
                .body("lead.id", notNullValue())
                .body("lead.name", equalTo(lead.getName()))
                .body("lead.email", equalTo(lead.getEmail()));
    }

    @Test(description = "Create lead without name returns validation error")
    public void testCreateLeadMissingName() {
        Map<String, Object> body = new HashMap<>();
        body.put("email", TestDataProvider.newLead().getEmail());
        body.put("priority", "Medium");
        body.put("status", "New");

        given().spec(authSpec)
                .body(body)
                .post("/leads")
                .then().statusCode(400).log().all()
                .body("success", equalTo(false))
                .body("error", equalTo("Validation failed"))
                .body("errors[0].message",equalTo("Name is required and must be a non-empty string"));
    }

    @Test(description = "Create lead without auth returns 401")
    public void testCreateLeadWithoutAuth() {
        Lead lead = TestDataProvider.newLead();

        given().spec(baseSpec)
                .body(leadToMap(lead))
                .post("/leads")
                .then().log().all().spec(unauthorizedSpec)
                .body("success", equalTo(false))
                .body("error", equalTo("Authorization header is required"));
    }

    @Test(description = "Create lead with high priority and qualified flag")
    public void testCreateHighPriorityLead() {
        Lead lead = TestDataProvider.newHighPriorityLead();

        given().spec(authSpec)
                .body(leadToMap(lead))
                .post("/leads")
                .then().statusCode(201).log().all()
                .body("success", equalTo(true))
                .body("lead.name", equalTo(lead.getName()))
                .body("lead.priority", equalTo("High"));
    }

    private Map<String, Object> leadToMap(Lead lead) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", lead.getName());
        map.put("email", lead.getEmail());
        map.put("priority", lead.getPriority());
        map.put("status", lead.getStatus());
        map.put("isQualified", lead.isQualified());
        map.put("emailOptIn", lead.isEmailOptIn());
        map.put("notes", lead.getNotes());
        return map;
    }
}
