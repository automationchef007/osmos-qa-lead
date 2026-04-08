package com.osmos.qa.driverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager implements BrowserDriver {

    @Override
    public WebDriver createDriver() {
        return new ChromeDriver();
    }
}
