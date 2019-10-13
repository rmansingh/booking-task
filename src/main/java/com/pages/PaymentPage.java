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
 * this class captures the actions and elements of payment page
 */
public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Get payment options details")
    public String getPaymentOptions(int index) {
        List<WebElement> paymentOptionList=driver.findElements(By.xpath("//*[@id='CheckoutPaymentOptions']//*[@class='payment-text t-body-highlight']"));
        return findElement(paymentOptionList.get(index)).getText();
    }
    @Step("Click continue to wait until for payment")
    public PaymentPage waitUntilForPaymentPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("info")));
        return this;
    }
    public int getPaymentList() {
        return driver.findElements(By.xpath("//*[@id='CheckoutPaymentOptions']//*[@class='payment-text t-body-highlight']")).size();
    }
}