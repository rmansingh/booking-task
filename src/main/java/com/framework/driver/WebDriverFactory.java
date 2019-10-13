package com.framework.driver;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @Author: Rupak Mansingh
 * @Desc: WebDriverFactory provides init method to initialize WebDriver for different os and browser combination
 */
public class WebDriverFactory {

    public static WebDriver init(String browser, String os) {
        return Browser.valueOf(browser.toUpperCase()).getDriver(os);
    }

    enum Browser {
        CHROME {
            public WebDriver getDriver(String os) {
                WebDriverManager manager = ChromeDriverManager.getInstance(DriverManagerType.CHROME);
                manager.setup();
                return new ChromeDriver();
            }
        },
        FIREFOX {
            public WebDriver getDriver(String os) {
                WebDriverManager manager = FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX);
                manager.setup();
                return new FirefoxDriver();
            }
        };

        public abstract WebDriver getDriver(String os);
    }
}