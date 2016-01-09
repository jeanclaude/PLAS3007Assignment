package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import org.junit.Assert;

import java.util.List;

/**
 * Created by jeanclaude.pace on 02/01/2016.
 */
public class EvernoteNotebookSteps {

    private EvernoteMainPage evernoteMainPage = new EvernoteMainPage();

    @When("^I create a notebook called \"(.*?)\"$")
    public void iCreateANotebookCalled(String notebookName) throws Throwable {
        evernoteMainPage.createNotebook(notebookName);
    }

    @And("^I create a note in notebook \"(.*?)\" with title \"(.*?)\" and description \"(.*?)\"$")
    public void iCreateANoteInNotebookWithTitleAndDescription(String notebookName, String noteTitle, String noteDescription) throws Throwable {
        evernoteMainPage.createNewNote(noteTitle,noteDescription);
        evernoteMainPage.setNotebookOfNote(notebookName);

    }

    @Then("^I can see the note in notebook \"(.*?)\" with title \"(.*?)\"$")
    public void iCanSeeTheNoteInNotebookWithTitle(String notebookName, String noteTitle) throws Throwable {
        evernoteMainPage.goToNotebook(notebookName);
        List<String> notesInList = evernoteMainPage.getNotesInList();
        Assert.assertTrue(notesInList.contains(noteTitle));
    }

    @And("^I cannot see the note with title \"(.*?)\" in the default notebook$")
    public void iCannotSeeTheNoteWithTitleInTheDefaultNotebook(String noteTitle) throws Throwable {
        evernoteMainPage.goToNotebook(evernoteMainPage.DEFAULT_NOTEBOOK_NAME);
        List<String> notesInList = evernoteMainPage.getNotesInList();
        Assert.assertFalse(notesInList.contains(noteTitle));
    }
}
