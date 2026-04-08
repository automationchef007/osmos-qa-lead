package com.osmos.qa.applicationpages;

import com.osmos.qa.base.BasePage;
import com.osmos.qa.models.Lead;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CreateLeadPage extends BasePage {

    private static final By LEAD_FORM_TITLE=By.xpath("//h2[text()='Create New Lead']");
    private static final By NAME_INPUT = By.id("create-name");
    private static final By EMAIL_INPUT = By.id("create-email");
    private static final By PHONE = By.id("create-phone");
    private static final By COMPANY = By.id("create-company");
    private static final By JOB_TITLE = By.id("create-job-title");
    private static final By INDUSTRY = By.id("create-industry");
    private static final By SOURCE = By.id("create-source");
    private static final By PRIORITY_SELECT = By.id("create-priority");
    private static final By STATUS_SELECT = By.id("create-status");
    private static final By NOTES_INPUT = By.id("create-notes");
    private static final By SUBMIT_BTN = By.xpath("//button[@data-testid='create-form-submit-btn']");
    private static final By LEAD_FORM = By.xpath("//div[@data-testid='modal-create-lead']");

    public CreateLeadPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Business Action : This method will fill the create lead form
     * @param lead
     * @return
     */
    public CreateLeadPage fillForm(Lead lead) {
        enter(NAME_INPUT, lead.getName());
        enter(EMAIL_INPUT, lead.getEmail());
        selectOption(PRIORITY_SELECT, lead.getPriority());
        selectOption(STATUS_SELECT, lead.getStatus());
        enter(NOTES_INPUT, lead.getNotes());
        return this;
    }

    /**
     * Business Action : This method will submit the form
     * @return
     */
    public DashboardPage submitForm() {
        click(SUBMIT_BTN);
        return new DashboardPage(driver);
    }

    /**
     * Business Action : This method will create lead by filling form and submitting it
     * @param lead
     * @return
     */
    public DashboardPage createLead(Lead lead) {
        fillForm(lead);
        return submitForm();
    }

    /**
     * Business Action : This method will select option from dropdown
     * @param locator
     * @param value
     */
    private void selectOption(By locator, String value) {
        WebElement element = waitForClickable(locator);
        element.click();
        List<WebElement> options = driver.findElements(By.xpath("//div[@role='option']/span"));
        for (WebElement option : options) {
            if (option.getText().equals(value)) {
                option.click();
                return;
            }
        }
    }

    /**
     * Business Action: This will check if lead form is present or not
     * @return
     */
    public boolean isPresent() {
        return isDisplayed(LEAD_FORM_TITLE);
    }

    public boolean isClosed() {
        return isNotDisplayed(LEAD_FORM_TITLE);
    }
}
