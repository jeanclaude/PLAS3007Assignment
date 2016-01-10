package edu.plas.plas3007.jcpassignment.pageobjectmodels;

import edu.plas.plas3007.jcpassignment.domainobjects.Note;
import edu.plas.plas3007.jcpassignment.driver.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class EvernoteMainPage {

    private WebDriver driver = Driver.getWebDriver();
    private Actions action = new Actions(driver);

    public final String DEFAULT_NOTEBOOK_NAME = "First Notebook";

    private void waitForPageToLoadCompletely() {
        //todo Can this be cleaner?
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // below doesn't work once you start creating your own notebooks!
//        WebDriverWait wait = new WebDriverWait(driver, 30);
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("gwt-debug-NotebookSelectMenu-notebookName"), "Notebook"));
    }

    public void createNewNote(String title,String description){
        createNewNoteWithoutSaving(title,description);
        saveNewNote();
    }

    public void createListOfNotes(List<Note> listOfNotes) {
        for (Note current : listOfNotes) {
            createNewNote(current.getTitle(), current.getDescription());
        }
    }

    /**
     * Creates a new note with a title and description without saving. REMOVE FOLLOWING IF NOT LONGER VALID Note that after the method executes the focus is left
     * in the description field.  The caller is hence required to switch to the default context (using driver.switchTo().defaultContent())
     * unless it wants to write into the description field
     * @param title
     * @param description
     */
    private void createNewNoteWithoutSaving(String title,String description){
        waitForPageToLoadCompletely();
        driver.findElement(By.id("gwt-debug-Sidebar-newNoteButton")).click();
        waitForPageToLoadCompletely();

        driver.findElement(By.id("gwt-debug-NoteTitleView-textBox")).sendKeys(title + Keys.ENTER);

        driver.switchTo().frame("entinymce_170_ifr");

        driver.findElement(By.id("tinymce")).sendKeys(description);

        driver.switchTo().defaultContent();
    }

    private void saveNewNote() {
        driver.findElement(By.id("gwt-debug-NoteAttributes-doneButton")).click();
    }

    public void createNewNoteWithTableInsideIt(String title,String description,int tableWidth,int tableHeight) {
        createNewNoteWithoutSaving(title,description);
        driver.findElement(By.id("gwt-debug-FormattingBar-tableButton")).click();
        List<WebElement> horizElements = driver.findElements(By.className("GOSDSN-CDN"));
        List<WebElement> vertElements = horizElements.get(tableWidth-1).findElements(By.className("GOSDSN-CEN"));
        vertElements.get(tableHeight-1).click();
        saveNewNote();
    }

    private WebElement findNoteInList (String noteTitle) {
        System.out.println("Searching for title " + noteTitle);
        try {
            //removed below since tests were not working when finding tags.
            //WebDriverWait wait = new WebDriverWait(driver, 10);
            //wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title"), noteTitle));
            //alas, replaced it by a Sleep. In an ideal world, we'll have time to change this
            try { Thread.sleep(1000); } catch (Exception e) {}
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
        showNotesList();
        Actions actions = new Actions(driver);
        WebElement itemToHoverOver;
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {
            while ((itemToHoverOver = driver.findElement(By.className("focus-NotesView-Note-hoverOverlay"))) != null) {
                actions.moveToElement(itemToHoverOver).build().perform();
                wait.until(ExpectedConditions.elementToBeClickable (driver.findElement(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-delete.qa-deleteButton")))).click();
                //driver.findElement(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-delete.qa-deleteButton")).click();
                wait.until((ExpectedConditions.elementToBeClickable(driver.findElement(By.id("gwt-debug-ConfirmationDialog-confirm"))))).click();
                try { Thread.sleep(1000); } catch (Exception e) {} //only required for Chrome -- could optimise to make browser-specific
            }
        } catch (Exception e) {
            System.out.println("All notes deleted");
        }

    }

    public void deleteNote(String title) {
        waitForPageToLoadCompletely();
        Actions actions = new Actions(driver);
        WebElement itemToHoverOver;
        List<WebElement> listOfNotes = driver.findElements(By.className("focus-NotesView-Note-innerSnippetContainer"));
        for (WebElement current : listOfNotes) {
            String noteName = current.findElement(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title")).getText();
            if (title.equals(noteName)) {
                System.out.println("Deleting note " + noteName);
                actions.moveToElement(current).build().perform();
                driver.findElement(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-delete.qa-deleteButton")).click();
                driver.findElement(By.id("gwt-debug-ConfirmationDialog-confirm")).click();
                break;
            }

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
        try { Thread.sleep(3000); } catch (Exception e) {}
        driver.findElement(By.id("gwt-debug-AccountMenu-logout")).findElement(By.cssSelector("div.GOSDSN-CCD.GOSDSN-CGJ.GOSDSN-CM2")).click();

        // if we don't sleep and then try to log in again, Evernote will ask us if we are sure we want to quit
        // we avoid this added complexity with a little wait
    }

    public void markNoteAsFavourite(String noteTitle) {
        Actions actions = new Actions(driver);
        WebElement requiredNote = findNoteInList(noteTitle);
        actions.moveToElement(requiredNote).build().perform();
        driver.findElement(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-shortcut.qa-shortcutButton")).click();
        //WebDriverWait wait = new WebDriverWait(driver, 30);
        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.focus-NotesView-Note-hoverIcon.focus-NotesView-Note-shortcut.qa-shortcutButton"))).click();
    }

    private void selectNote(String noteTitle) {
        findNoteInList(noteTitle).click();
    }

    public void showShortcutsList() {
        waitForPageToLoadCompletely();
        driver.findElement(By.id("gwt-debug-Sidebar-shortcutsButton-container")).findElement(By.className("GOSDSN-COQ")).click();
        waitForPageToLoadCompletely();
    }

    public void showNotesList() {
        waitForPageToLoadCompletely();

        driver.switchTo().defaultContent();
//        WebDriverWait wait = new WebDriverWait(driver, 30);
//        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("gwt-debug-Sidebar-notesButton-container")).findElement(By.className("GOSDSN-COQ")))).click();

        driver.findElement(By.id("gwt-debug-Sidebar-notesButton-container")).findElement(By.className("GOSDSN-COQ")).click();
        try { Thread.sleep(1000); } catch (Exception e) {};  //we need to wait for the notes to load
    }

    public void showNotebooksList() {
        System.out.println("Going to the notebooks page");
        showNotesList();
        driver.findElement(By.id("gwt-debug-Sidebar-notebooksButton-container")).findElement(By.className("GOSDSN-COQ")).click();
        try { Thread.sleep(1000); } catch (Exception e) {}; //we need to wait for the notes to load
    }

    public void showTagsList() {
        System.out.println("Going to the notebooks page");
        showNotesList();
        driver.findElement(By.id("gwt-debug-Sidebar-tagsButton-container")).findElement(By.className("GOSDSN-COQ")).click();
        try { Thread.sleep(1000); } catch (Exception e) {}; //we need to wait for the notes to load
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

    /**
     * Checks whether the note with the given title has a table inside it with the specified width and height
     * @param noteTitle
     * @param tableWidth
     * @param tableHeight
     * @return True if the note is found to have a table inside it of the correct size. False otherwise
     */
    public boolean checkNoteHasTableInsideIt(String noteTitle, int tableWidth, int tableHeight) {
        selectNote(noteTitle);
        driver.switchTo().frame("EN_IframePanel_0");
        WebElement tableElement = driver.findElement(By.tagName("table"));
        if (tableElement==null) {
            System.out.println("Table element not found");
            return false;
        }
        List<WebElement> trElements = tableElement.findElements(By.tagName("tr"));
        if (trElements.size()!=(tableWidth)) {
            System.out.println("TR list has wrong size: " + trElements.size());
            return false;
        }
        for (WebElement currentTR : trElements) {
            List<WebElement> tdElements = currentTR.findElements(By.tagName("td"));
            if (tdElements.size()!=(tableHeight)) {
                System.out.println("TD list has wrong size: " + tdElements.size());
                return false;
            }
            System.out.println("TD List has correct size");
        }
        System.out.println("Table of correct size found!");
        return true;
    }

    private void sortNotesList(String sortOrder) {
        driver.findElement(By.className("focus-NotesView-Subheader-OptionsButton")).click();
        List<WebElement> sortOptions = driver.findElements(By.className("SelectorOption"));
        for (WebElement currentOption : sortOptions) {
            if (currentOption.getText().equals(sortOrder)) {
                System.out.println("Clicking option to sort by " + sortOrder);
                currentOption.click();
                try {
                    //give the notes list time to refresh
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public List<String> getNotesInList() {
        System.out.println("Getting the notes in the current notes list");
        List<String> returnList = new ArrayList<String>();
        List<WebElement> foundElements = driver.findElements(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title"));
        for (WebElement current : foundElements) {
            System.out.println("Adding to list: " + current.getText());
            returnList.add(current.getText());
        }
        return returnList;
    }

    public List<String> getNotesBySortOrder(String sortOrder) {
        showNotesList();
        sortNotesList(sortOrder);
        return getNotesInList();
    }

    public void searchForText(String searchString) {
        showNotesList(); // this is done to ensure that any current search is cleared
        driver.findElement(By.id("gwt-debug-Sidebar-searchButton-container")).findElement(By.className("GOSDSN-COQ")).click();
        driver.findElement(By.id("gwt-debug-searchViewSearchBox")).sendKeys(searchString + Keys.ENTER);
        waitForPageToLoadCompletely();
    }

    /**
     * This method assumes that there is currently a note shown on the page
     * @return
     */
    public String getCurrentNoteTitle() {
        return driver.findElement(By.id("gwt-debug-NoteTitleView-textBox")).getAttribute("value");
    }

    public void createNotebook(String notebookName) {
        showNotebooksList();
        driver.findElement(By.id("gwt-debug-NotebooksDrawer-createNotebookButton")).click();
        driver.findElement(By.id("gwt-debug-CreateNotebookDialog-centeredTextBox-textBox")).sendKeys(notebookName + Keys.ENTER);
    }

    public void deleteAllNotebooksExceptDefault() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        showNotebooksList();
        List<WebElement> notebooksList;
        //while (((notebooksList = driver.findElements(By.cssSelector("div.GOSDSN-CGQB.GOSDSN-CHD.qa-name")))!=null)
        //        && notebooksList.size()>1) {
        while (((notebooksList = driver.findElements(By.cssSelector("div.GOSDSN-CKQB.qa-notebookWidget.dragdrop-draggable.dragdrop-handle")))!=null)
                && notebooksList.size()>1) {
            System.out.println("Notebooks list size = " + notebooksList.size());
            WebElement currentNotebookNode=notebooksList.get(0);
            String currentNotebookName = currentNotebookNode.findElement(By.cssSelector("div.GOSDSN-CGQB.GOSDSN-CHD.qa-name")).getText();
            System.out.println("Current notebook title is " + currentNotebookName);
            if (currentNotebookName.equals(DEFAULT_NOTEBOOK_NAME)) {
                System.out.println("Selecting second node");
                currentNotebookNode=notebooksList.get(1);
                currentNotebookName = currentNotebookNode.findElement(By.cssSelector("div.GOSDSN-CGQB.GOSDSN-CHD.qa-name")).getText();
            }
            System.out.println("Current notebook name = " + currentNotebookName);
            try {
                action.moveToElement(currentNotebookNode).build().perform();
                wait.until(ExpectedConditions.elementToBeClickable(currentNotebookNode.findElement(By.cssSelector("div.GOSDSN-CJPB.GOSDSN-CBQB.qa-deleteButton.GOSDSN-CLPB")))).click();
                //currentNotebookNode.findElement(By.cssSelector("div.GOSDSN-CJPB.GOSDSN-CBQB.qa-deleteButton.GOSDSN-CLPB")).click();

                driver.findElement(By.id("gwt-debug-ConfirmationDialog-confirm")).click();
                //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("gwt-debug-NotebooksDrawer-title"))));;

            } catch (Exception e) {
                System.out.println("Element not clickable when trying to delete notebook " + currentNotebookName + " due to " + e.getMessage());
            }
            showNotebooksList();
        }
    }

    public void setNotebookOfNote(String notebookName) {
        driver.findElement(By.id("gwt-debug-NotebookSelectMenu-notebookName")).click();
        List<WebElement> notebookNames = driver.findElements(By.id("div.GOSDSN-CFMB.GOSDSN-CHD.qa-name"));
        for (WebElement current : notebookNames) {
            System.out.println("Current notebook from list is " + current.getText());
            if (current.getText().equals(notebookName)) {
                System.out.println("It's what I was looking for! Clicking it!");
                current.click();
                break;
            }
        }
        try { Thread.sleep(1000); } catch (Exception e) {};  //without this we go too fast
    }

    public void goToNotebook(String notebookName) {
        System.out.println("Going to notebook: " + notebookName);
        showNotebooksList();
        List<WebElement> notebooksList = driver.findElements(By.cssSelector("div.GOSDSN-CGQB.GOSDSN-CHD.qa-name"));
        for (WebElement current : notebooksList) {
            System.out.println("Going through list of notebooks. Current item is " + current.getText());
            if (current.getText().equals(notebookName)) {
                System.out.println("Clicking on current notebook name since it is: " + current.getText() );
                current.click();
                break;
            }
        }
        try { Thread.sleep(1000); } catch (Exception e) {};  //we have to wait a bit until the list of notes gets refreshed
    }

    public void goToTrashCan() {
        System.out.println("Going to trashcan");
        showNotebooksList();
        driver.findElement(By.cssSelector("div.GOSDSN-CBD.GOSDSN-CGQB")).click();

        try { Thread.sleep(3000); } catch (Exception e) {};  //we have to wait a bit until the list of notes gets refreshed
        //something like the below would work better. As it is, it still doesn't work well (probably because the first note in the list
        //changes from "Loading..." before the other ones. However, try to fine-tune when possible
//        WebDriverWait wait = new WebDriverWait(driver, 30);
//        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title"),"Loading...")));
    }

    public void emptyTrash() {
        goToTrashCan();
        driver.findElement(By.cssSelector("button.GOSDSN-CC0B.GOSDSN-CA0B.GOSDSN-CLD")).click();
        driver.findElement(By.id("gwt-debug-ConfirmationDialog-confirm")).click();
    }

    public void restoreFromTrash(String title) {
        goToTrashCan();

        Actions actions = new Actions(driver);
        List<WebElement> listOfNotes = driver.findElements(By.className("focus-NotesView-Note-innerSnippetContainer"));
        for (WebElement current : listOfNotes) {
            String noteName = current.findElement(By.cssSelector("div.focus-NotesView-Note-noteTitle.qa-title")).getText();
            if (title.equals(noteName)) {
                actions.moveToElement(current).build().perform();
                List<WebElement> buttons = current.findElements(By.className("focus-NotesView-Note-hoverButton"));
                for (WebElement currentButton : buttons) {
                    if (currentButton.getText().equals("Restore")) {
                        currentButton.click();
                        break;
                    }
                }
                break;
            }
        }

    }

    public void tagNote(String noteName, String tagName) {
        System.out.println("Tagging " + noteName + " with tag " + tagName);
        showNotesList();
        selectNote(noteName);
        try { Thread.sleep(4000); } catch (Exception e) {e.printStackTrace();};  //we have to wait a bit for the field to get focus
        driver.switchTo().defaultContent();
        driver.findElement(By.id("gwt-debug-NoteTagsView-tagInputBox")).click();
        System.out.println("Clicked on text box");
        try { Thread.sleep(4000); } catch (Exception e) {e.printStackTrace();};  //we have to wait a bit for the field to get focus
        driver.findElement(By.cssSelector("input.GOSDSN-CCLE.GOSDSN-CDLE.qa-ResizingSuggestLozenge-input")).sendKeys(tagName);
        driver.findElement(By.cssSelector("input.GOSDSN-CCLE.GOSDSN-CDLE.qa-ResizingSuggestLozenge-input")).sendKeys(Keys.ENTER);
    }

    public void selectTag(String tagName) {
        showTagsList();
        List<WebElement> tagsList = driver.findElements(By.cssSelector("div.focus-drawer-TagsDrawer-TagSelectable-name.qa-name"));
        for (WebElement current : tagsList) {
            if (tagName.equals(current.getText())) {
                System.out.println("Selecting the tag " + tagName);
                current.click();
                try { Thread.sleep(1000); } catch (Exception e) {}
                break;
            }
        }
    }

    public void deleteTag(String tagName) {
        Actions actions = new Actions(driver);
        showTagsList();
        List<WebElement> tagNodes = driver.findElements(By.cssSelector("div.qa-tagWidget.focus-drawer-TagsDrawer-Tag-input-switcher"));
        for (WebElement currentTagNode : tagNodes) {
            WebElement currentName = currentTagNode.findElement(By.cssSelector("div.focus-drawer-TagsDrawer-TagSelectable-name.qa-name"));
            if (tagName.equals(currentName.getText())) {
                actions.moveToElement(currentName).build().perform();
                driver.findElement(By.cssSelector("div.focus-drawer-TagsDrawer-TagSelectable-icon.focus-drawer-TagsDrawer-TagSelectable-delete-icon.qa-editButton")).click();
                driver.findElement(By.id("gwt-debug-ConfirmationDialog-confirm")).click();
                break;
            }
        }

    }
}
