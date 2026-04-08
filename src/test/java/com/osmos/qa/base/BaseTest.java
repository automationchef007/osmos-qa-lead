package com.osmos.qa.base;

import com.osmos.qa.config.ConfigManager;
import com.osmos.qa.driverManager.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(ConfigManager.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    public static void addTestStep(String message)
    {
        System.out.println(message);
    }
}
