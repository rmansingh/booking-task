package com.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Rupak Mansingh
 * this class captures the actions and elements of traveller info page
 */
public class TravellerInfoPage extends BasePage {

    @FindBy(name = "primary-passenger-name_prefix")
    WebElement salutation;

    @FindBy(name = "primary-passenger-first_name")
    WebElement firstName;

    @FindBy(name = "primary-passenger-last_name")
    WebElement lastName;

    @FindBy(name = "primary-passenger-email")
    WebElement eMailAddress;

    @FindBy(name = "primary-passenger-email_confirmation")
    WebElement confirmEmailAddress;

    @FindBy(xpath = "//*[@class='form-checkbox is-error']")
    WebElement errorCheckBox;

    @FindBy(xpath = "//*[@class='form-checkbox is-error']//*[@class='form-icon']")
    WebElement termsAndConditions;

    @FindBy(id = "CheckoutButtonNext")
    WebElement continueToPayment;

    public TravellerInfoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Enter customer details info first name,last name,email")
    public TravellerInfoPage enterCustomerInfo(String salutationValue,String firstNameValue,String lastNameValue,String emailValue) {
        waitForVisibilityOf(salutation);
        salutation.sendKeys(salutationValue);
        firstName.sendKeys(firstNameValue);
        lastName.sendKeys(lastNameValue);
        eMailAddress.sendKeys(emailValue);
        confirmEmailAddress.sendKeys(emailValue);
        return this;
    }

    public boolean isErrorInTermsAndConditions(){
        waitForVisibilityOf(errorCheckBox);
        return errorCheckBox.isDisplayed();
    }

    @Step("Click terms and conditions checkbox")
    public TravellerInfoPage clickTermsAndConditions() {
        waitForVisibilityOf(termsAndConditions);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsAndConditions);
        clickJavaScript(termsAndConditions);
        return this;
    }

    @Step("Click continue to payment")
    public PaymentPage clickContinueToPayment() {
        waitForVisibilityOf(continueToPayment);
        continueToPayment.click();
        return new PaymentPage(driver);
    }
}