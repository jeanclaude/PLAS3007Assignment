package edu.plas.plas3007.jcpassignment.pageobjectmodels;

import edu.plas.plas3007.jcpassignment.driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class EvernoteLoginPage {
    private WebDriver driver = Driver.getWebDriver();

    public void populateUsername(String username) {
        driver.findElement(By.id("username")).sendKeys(username);
    }

    public void populatePassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    public void clickLoginButton(){
        driver.findElement(By.id("login")).click();
    }

    public String getLoginPageErrorMessage() {
        return driver.findElement(By.cssSelector("div.error-status.FieldState-message.FieldState_error-message")).getText();
    }


}
