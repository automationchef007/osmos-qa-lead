package com.osmos.qa.applicationpages;

import com.osmos.qa.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.cssSelector("[data-testid='login-email-input']");
    private static final By PASSWORD_INPUT = By.cssSelector("[data-testid='login-password-input']");
    private static final By SUBMIT_BTN = By.cssSelector("[data-testid='login-submit-btn']");
    private static final By LOGIN_FORM = By.cssSelector("[data-testid='login-form']");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitForVisible(LOGIN_FORM);
    }

    /**
     * Business Action: Login to the application
     * @param email
     * @param password
     * @return
     */
    public DashboardPage loginToApplication(String email, String password) {
        enter(EMAIL_INPUT, email);
        enter(PASSWORD_INPUT, password);
        click(SUBMIT_BTN);
        return new DashboardPage(driver);
    }

}
