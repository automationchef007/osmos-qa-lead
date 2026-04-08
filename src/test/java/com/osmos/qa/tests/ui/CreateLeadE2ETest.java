package com.osmos.qa.tests.ui;

import com.osmos.qa.models.Lead;
import com.osmos.qa.applicationpages.CreateLeadPage;
import com.osmos.qa.applicationpages.DashboardPage;
import com.osmos.qa.applicationpages.LoginPage;
import com.osmos.qa.utils.TestDataProvider;
import com.osmos.qa.base.BaseTest;
import com.osmos.qa.config.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateLeadE2ETest extends BaseTest {

    @Test(description = "Login → Create Lead → Verify lead appears in dashboard list")
    public void testLoginCreateLeadAndVerifyInList() {
        Lead lead = TestDataProvider.newLead();

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);

        //Login to the application
        DashboardPage dashboard = loginPage.loginToApplication(ConfigManager.get("default.email"), ConfigManager.get("default.password"));
        addTestStep("Logged into the application");

        Assert.assertTrue(dashboard.isLoaded(), "Dashboard should load after login");

        // Step 2: Create Lead
        CreateLeadPage createLeadPage = dashboard.clickCreateLead();
        addTestStep("Clicked on create lead button");

        dashboard = createLeadPage.createLead(lead);
        addTestStep("Created a new lead with name '" + lead.getName() + "'");

        // Step 3: Verify lead in list
        Assert.assertFalse(createLeadPage.isPresent(), "Create new lead popup should be closed");
        Assert.assertTrue(dashboard.isLoaded(), "Dashboard should reload after lead creation");
        Assert.assertTrue(dashboard.isLeadPresent(lead.getName()),
                "Newly created lead '" + lead.getName() + "' should appear in the leads list");
    }
}
