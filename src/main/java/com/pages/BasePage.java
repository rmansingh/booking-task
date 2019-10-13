package com.pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//*[@class='t-subheader-highlight c-text-default']")
    WebElement totalPrice;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10, 50);
        PageFactory.initElements(driver, this);
    }

    public WebElement waitForVisibilityOf(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public Boolean waitForInVisibilityOf(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    @Step("Find element: {element}")
    protected WebElement findElement(WebElement element) {
        return this.wait.until(ExpectedConditions.visibilityOf(element));
    }

    public float getTotalPrice(){
        waitForVisibilityOf(totalPrice);
        return parsePrice(totalPrice.getText());
    }

    protected Float parsePrice(String value) {
        String sanitizedValue = removeNonDigitsOrComma(value).replace(",", ".");
        return NumberUtils.createFloat(sanitizedValue);
    }

    protected String removeNonDigitsOrComma(String value) {
        return value.replaceAll("[^\\d.,]", "");
    }

    @Step("Enter: {text} into {by} with delay: {charTimeoutInMs}")
    protected void enterTextWithDelay(WebElement element, String text, int charTimeoutInMs) {
        element.clear();
        char[] var5 = text.toCharArray();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            char symbol = var5[var7];
            element.sendKeys(new CharSequence[]{String.valueOf(symbol)});

            try {
                Thread.sleep((long)charTimeoutInMs);
            } catch (InterruptedException var10) {
                var10.printStackTrace();
            }
        }

        this.unFocusInput(element);
    }

    private void unFocusInput(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
    }

    @Step("Click on {element} using JavaScript")
    protected void clickJavaScript(WebElement element) {
        this.executeJavaScript("arguments[0].click();", element);
    }
    @Step
    protected Object executeJavaScript(String script, Object... objects) {
        return ((JavascriptExecutor)this.driver).executeScript(script, objects);
    }
}