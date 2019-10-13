package com.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Rupak Mansingh
 * this class captures the actions and elements of home page
 */
public class HomePage extends BasePage {

    @FindBy(id = "search_departure_autocompleted")
    WebElement departureCity;

    @FindBy(id = "search_arrival_autocompleted")
    WebElement arrivalCity;

    @FindBy(xpath = "//*[@class='datepicker input-lg departure_on hasDatepicker']")
    WebElement departureDate;

    @FindBy(css = "#search-button-options #submit-search-btn")
    WebElement searchButton;

    @FindBy(xpath = "//*[@class='cr']")
    WebElement uncheckDeutscheBahn;

    @FindBy(className = ".ui-datepicker-next")
    WebElement calendarNextButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Enter departure city")
    public HomePage enterDepartureCity(String departureCityValue) {
        waitForVisibilityOf(departureCity);
        enterTextWithDelay(departureCity, departureCityValue, 300);
        return this;
    }

    @Step("Uncheck the compare with deutsche bahn")
    public HomePage uncheckCompareWithDeutscheBahn() {
        waitForVisibilityOf(uncheckDeutscheBahn);
        uncheckDeutscheBahn.click();
        return this;
    }

    @Step("Enter arrival city")
    public HomePage enterArrivalCity(String arrivalCityValue) {
        waitForVisibilityOf(arrivalCity);
        enterTextWithDelay(arrivalCity, arrivalCityValue, 300);
        return this;
    }

    @Step("Enter departure date")
    public HomePage enterDepartureDate() {
        departureDate.click();
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(14);
        String calendarMonth = driver.findElement(By.className("ui-datepicker-month")).getText();
        if (!calendarMonth.equalsIgnoreCase(localDateTime.getMonth().toString())) {
            calendarNextButton.click();
        }
        String dateLocator = "//a[contains(.,'%s')]";
        driver.findElement(By.xpath(String.format(dateLocator, localDateTime.getDayOfMonth()))).click();
        return this;
    }

    @Step("Click on search button")
    public SearchPage search() {
        waitForVisibilityOf(searchButton);
        clickJavaScript(searchButton);
        return new SearchPage(driver);
    }

    public HomePage openHomePage() {
        driver.get(System.getProperty("baseurl"));
        return this;
    }

    public HomePage dismissCookieMessage() {
        driver.findElement(By.cssSelector("a.cc-dismiss")).click();
        waitForInVisibilityOf(driver.findElement(By.cssSelector("a.cc-dismiss")));
        return this;
    }
}
