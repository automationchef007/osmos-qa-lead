package com.osmos.qa.driverManager;

import com.osmos.qa.config.ConfigManager;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static BrowserDriver getDriver(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                return new ChromeDriverManager();
            case "firefox":
                return new FirefoxDriverManager();
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserType);
        }
    }

    public static WebDriver createDriver() {
        String browser = ConfigManager.getBrowser();
        BrowserDriver browserDriver = getDriver(browser);
        WebDriver webDriver = browserDriver.createDriver();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getImplicitWait()));
        webDriver.manage().window().maximize();
        driver.set(webDriver);
        return webDriver;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}
