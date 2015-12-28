package edu.plas.plas3007.jcpassignment.pageobjectmodels;

import edu.plas.plas3007.jcpassignment.driver.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class EvernoteMainPage {

    private WebDriver driver = Driver.getWebDriver();
    private Actions action = new Actions(driver);

    private void waitForPageToLoadCompletely() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("gwt-debug-NotebookSelectMenu-notebookName"), "Notebook"));
    }

    public void createNewNote(String title,String description){
        waitForPageToLoadCompletely();
        driver.findElement(By.id("gwt-debug-Sidebar-newNoteButton")).click();
        waitForPageToLoadCompletely();

        driver.findElement(By.id("gwt-debug-NoteTitleView-textBox")).sendKeys(title + Keys.ENTER);

       // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.GOSDSN-CJGB.GOSDSN-COFB")));
        driver.switchTo().frame("entinymce_170_ifr");

        driver.findElement(By.id("tinymce")).sendKeys(description);
        //driver.findElement(By.id("gwt-debug-NoteTitleView-textBox")).sendKeys(description);
        //driver.findElement(By.cssSelector("div.GOSDSN-CCD.GOSDSN-CCHB")).sendKeys(description);
        System.out.println("Checkpoint 3");

        driver.switchTo().defaultContent();
        driver.findElement(By.id("gwt-debug-NoteAttributes-doneButton")).click();

    }

    private WebElement findNoteInList (String noteTitle) {
        System.out.println("Searching for title " + noteTitle);
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title"), noteTitle));
            List<WebElement> foundElements = driver.findElements(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title"));
            for (WebElement current : foundElements) {
                if (current.getText().equals(noteTitle)) {
                    System.out.println("Found " + noteTitle);
                    return current;
                }
            }
            return null;
        } catch (TimeoutException e) {
            System.out.println("Did NOT find " + noteTitle);
            return null;
        }
    }

    public boolean noteWithTitleExists(String noteTitle) {
        System.out.println("Searching for title " + noteTitle);
        return (findNoteInList(noteTitle)!=null);
    }

    public void deleteAllNotes() {
        waitForPageToLoadCompletely();
        Actions actions = new Actions(driver);
        WebElement itemToHoverOver;
        try {
            while ((itemToHoverOver = driver.findElement(By.className("focus-NotesView-Note-hoverOverlay"))) != null) {
                actions.moveToElement(itemToHoverOver).build().perform();
                driver.findElement(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-delete.qa-deleteButton")).click();
                driver.findElement(By.id("gwt-debug-ConfirmationDialog-confirm")).click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("All notes deleted");
        }

    }

    private void openAccountMenu() {
        waitForPageToLoadCompletely();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("GOSDSN-CJP"))).click();
        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.GOSDSN-CEG.GOSDSN-CPD"))).click();
    }

    public String getLoggedInUsername() {
        openAccountMenu();
        return driver.findElement((By.id("gwt-debug-AccountMenu-name"))).getText();
    }

    public boolean userIsLoggedIn(String username) {
        System.out.println("Checking whether " + username + " is logged in");
        String loggedInUsername = getLoggedInUsername();
        System.out.println("Logged in username is " + loggedInUsername);
        return loggedInUsername.equalsIgnoreCase(username);
    }

    public void logoutUser() {
        openAccountMenu();
        driver.findElement(By.id("gwt-debug-AccountMenu-logout")).findElement(By.cssSelector("div.GOSDSN-CCD.GOSDSN-CGJ.GOSDSN-CM2")).click();
    }

    public void markNoteAsFavourite(String noteTitle) {
        Actions actions = new Actions(driver);
        WebElement requiredNote = findNoteInList(noteTitle);
        actions.moveToElement(requiredNote).build().perform();
        driver.findElement(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-shortcut.qa-shortcutButton")).click();
        //WebDriverWait wait = new WebDriverWait(driver, 30);
        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-shortcut.qa-shortcutButton"))).click();
    }

    private void showShortcutsList() {
        waitForPageToLoadCompletely();
        driver.findElement(By.id("gwt-debug-Sidebar-shortcutsButton-container")).findElement(By.className("GOSDSN-COQ")).click();
    }

    public boolean noteExistsInShortcutList(String noteTitle) {
        showShortcutsList();
        List<WebElement> foundElements = driver.findElements(By.cssSelector("div.GOSDSN-CMVB.GOSDSN-CHD.qa-name"));
        for (WebElement current : foundElements) {
            if (current.getText().equals(noteTitle)) {
                return true;
            }
        }
        return false;
    }
}
