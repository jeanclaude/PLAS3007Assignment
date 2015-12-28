package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import org.junit.Assert;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class EvernoteNotesCreationSteps {

    private final String TEST_TITLE = "Note Created by Auto Test";
    private final String TEST_DESCRIPTION = "Test description created automatically";
    private EvernoteMainPage evernoteMainPage = new EvernoteMainPage();

    @When("^I create a new note$")
    public void iCreateANewNote() throws Throwable {
        evernoteMainPage.createNewNote(TEST_TITLE,TEST_DESCRIPTION);
    }

    @Then("^I can see the new note$")
    public void iCanSeeTheNewNote() throws Throwable {
        Assert.assertTrue(evernoteMainPage.noteWithTitleExists(TEST_TITLE));
    }

    @Then("^delete all notes$")
    public void deleteAllNotes() throws Throwable {
        evernoteMainPage.deleteAllNotes();
    }

    @And("^I mark it as favourite$")
    public void iMarkItAsFavourite() throws Throwable {
        evernoteMainPage.markNoteAsFavourite(TEST_TITLE);
    }

    @Then("^I can see the new note listed under shortcuts$")
    public void iCanSeeTheNewNoteListedUnderShortcuts() throws Throwable {
        Assert.assertTrue(evernoteMainPage.noteExistsInShortcutList(TEST_TITLE));
    }
}
