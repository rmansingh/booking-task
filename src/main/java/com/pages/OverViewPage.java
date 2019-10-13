package com.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * @author Rupak Mansingh
 * this class captures the actions and elements of overview page
 */
public class OverViewPage extends BasePage {

    @FindBy(id = "CheckoutButtonNext")
    WebElement continueTravellerInfo;

    public OverViewPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Select flexibility and comfort class")
    public OverViewPage selectFlexibilityAndComfortClassOption() {
        List<WebElement> list = driver.findElements(By.xpath("//*[@class='form-icon']"));
        findElement(list.get(4)).click();
        findElement(list.get(1)).click();
        return this;
    }

    @Step("Click continue to traveller info")
    public TravellerInfoPage clickContinueToTravellerInfo() {
        waitForVisibilityOf(continueTravellerInfo);
        continueTravellerInfo.click();
        wait.until(ExpectedConditions.urlContains("info"));
        return new TravellerInfoPage(driver);
    }
}
