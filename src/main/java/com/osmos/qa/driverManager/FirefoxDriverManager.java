package com.osmos.qa.driverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager implements BrowserDriver {

    @Override
    public WebDriver createDriver() {
        return new FirefoxDriver();
    }
}
