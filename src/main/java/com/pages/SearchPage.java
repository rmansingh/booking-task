package com.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * @author Rupak Mansingh
 * this class captures the actions and elements of search page
 */
public class SearchPage extends BasePage {

    @FindBy(xpath = "//*[@class='form-icon checkbox__icon']")
    WebElement transportType;

    @FindBy(xpath = "//*[@class='search-trip-card-button__traveller']")
    WebElement selectTrain;

    @FindBy(xpath = "//*[@class='bt bt-fare']")
    WebElement chooseFare;

    @FindBy(xpath = "//*[@title='Got it']")
    WebElement gotItPopUp;

    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public Boolean isTransportTypeVisible() {
        waitForVisibilityOf(transportType);
        return transportType.isDisplayed();
    }

    @Step("Select ICE train")
    public SearchPage selectTrain() {
        selectVehicleType();
        return this;
    }

    @Step("Select first option from the list of trains")
    public SearchPage selectFirstOption() {
        waitForVisibilityOf(selectTrain);
        findElement(selectTrain).click();
        return this;
    }

    @Step("Select first option from the list of trains")
    public OverViewPage clickChooseFare() {
        waitForVisibilityOf(chooseFare);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chooseFare);
        findElement(chooseFare).click();
        wait.until(ExpectedConditions.urlContains("overview"));
        return new OverViewPage(driver);
    }

    private void selectVehicleType() {
        List<WebElement> list = driver.findElements(By.xpath("//*[@class='form-icon checkbox__icon']"));
        for (int i = 1; i < list.size(); i++) {
            findElement(list.get(i)).click();
        }
    }
    public SearchPage closeEnvironmentalPopUp() {
        waitForVisibilityOf(gotItPopUp);
        gotItPopUp.click();
        return this;
    }
}
