package com.osmos.qa.applicationpages;

import com.osmos.qa.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DashboardPage extends BasePage {

    private static final By LEADS_TABLE = By.cssSelector("table");
    private static final By LEAD_ROWS = By.cssSelector("table tbody tr");
    private static final By CREATE_LEAD_BTN = By.xpath("//button[@data-testid='leads-create-new-btn']");
    private static final By DASHBOARD_HEADING = By.xpath("//h1[@data-testid='leads-title']");
    private static final By USER_INFO=By.xpath("//div[@data-testid='leads-user-info']");
    private static final By SEARCH_INPUT = By.cssSelector("input[placeholder*='Search'], input[placeholder*='search']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Business Action : This method checks if dashboard is loaded or not
     * @return
     */
    public boolean isLoaded() {
        try {
            waitForVisible(DASHBOARD_HEADING);
            waitForVisible(USER_INFO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Business Action : This method will click on create lead button
     * @return
     */
    public CreateLeadPage clickCreateLead() {
        click(CREATE_LEAD_BTN);
        return new CreateLeadPage(driver);
    }


    public boolean isLeadPresent(String leadName) {
        By LEAD_NAME= By.xpath("//table[@data-testid='leads-table']/tbody/tr/td[text()='"+leadName+"']");
        try {
            waitForVisible(LEAD_NAME);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
