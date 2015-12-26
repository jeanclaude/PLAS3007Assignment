package edu.plas.plas3007.jcpassignment.pageobjectmodels;

import edu.plas.plas3007.jcpassignment.driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    public String getLoggedInUsername() {
        driver.findElement(By.cssSelector("div.GOSDSN-CEG.GOSDSN-CPD")).click();
        return driver.findElement((By.id("gwt-debug-AccountMenu-name"))).getText();
    }

    public boolean userIsLoggedIn(String username) {
        System.out.println("Checking whether " + username + " is logged in");
        String loggedInUsername = getLoggedInUsername();
        System.out.println("Logged in username is " + loggedInUsername);
        return loggedInUsername.equalsIgnoreCase(username);
    }

    public String getLoginPageErrorMessage() {
        return driver.findElement(By.cssSelector("div.error-status.FieldState-message.FieldState_error-message")).getText();
    }




}
