package com.base;

import com.framework.driver.WebDriverFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Rupak Mansingh
 * @Desc: Base class provides all the members & functions to be made visible for uiTest classes and page objects
 */
public class BaseTest {

    private WebDriver driver;
    String browser, os;

    private static final Logger log = Logger.getLogger(BaseTest.class.getName());

    @BeforeMethod
    @Parameters({"browser", "os"})
    public void setUp(@Optional("chrome") String browser, @Optional("mac") String os) {
        /*Initializing log4j.*/
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        log("Initializing web driver.");
        this.browser = browser;
        this.os = os;
        driver = WebDriverFactory.init(browser, os);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void closeBrowser() {
        driver.quit();
        log("Browser Closed");
    }

    /**
     * Method to be used for getting the status of the uiTest execution and close the driver
     */
    @AfterMethod
    public void quiteDriverAfterTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenShot(result.getMethod().getMethodName());
        }
        closeBrowser();
    }

    /**
     * Method used for getting the screen capture with the name in a particular format
     *
     * @param methodName
     */
    public void takeScreenShot(String methodName) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()
                    + "/src/uiTest/resources/failure_screencaptures/";
            File destFile = new File(String.format("%sFailure_ScreenCaptures_%s_%s_%s_%s.png", reportDirectory, os, browser, methodName, formater.format(calendar.getTime())));
            FileHandler.copy(srcFile, destFile);
            Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
                    + "' height='100' width='100'/> </a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logging method so that the same log is added in logger as well as in TestNG Report
     *
     * @param data
     */
    public void log(String data) {
        log.info(data);
        Reporter.log(data + "\n");
    }

    public WebDriver getDriver() {
        return driver;
    }

}
