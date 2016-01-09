package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.domainobjects.Note;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import org.junit.Assert;

import java.util.List;

/**
 * Created by jeanclaude.pace on 02/01/2016.
 */
public class EvernoteTrashCanSteps {

    private EvernoteMainPage evernoteMainPage = new EvernoteMainPage();

    @When("^I create the following notes:$")
    public void iCreateTheFollowingNotes(List<Note> listOfNotes) throws Throwable {
        evernoteMainPage.createListOfNotes(listOfNotes);
    }

    @And("^I go to the TrashCan$")
    public void iGoToTheTrashCan() throws Throwable {
        evernoteMainPage.goToTrashCan();
    }

    @Then("^I can see the following notes:$")
    public void iCanSeeTheFollowingNotes(List<String> noteTitles) throws Throwable {
        List<String> notesInList = evernoteMainPage.getNotesInList();
        for (String currentTitle : noteTitles) {
            Assert.assertTrue("Shown notes list contains the note " + currentTitle, notesInList.contains(currentTitle));
        }
    }

    @When("^I empty the TrashCan$")
    public void iEmptyTheTrashCan() throws Throwable {
        evernoteMainPage.emptyTrash();
    }

    @Then("^The TrashCan is empty$")
    public void theTrashCanIsEmpty() throws Throwable {
        evernoteMainPage.goToTrashCan();
        List<String> notesInList = evernoteMainPage.getNotesInList();
        Assert.assertTrue("Checking that the trashcan is empty", notesInList.size()==0 );
    }

    @And("^I delete the note with title \"(.*?)\"$")
    public void iDeleteTheNoteWithTitle(String title) throws Throwable {
        evernoteMainPage.showNotesList();
        evernoteMainPage.deleteNote(title);
    }

    @And("^I go to the TrashCan and restore the note with title \"(.*?)\"$")
    public void iGoToTheTrashCanAndRestoreTheNoteWithTitle(String title) throws Throwable {
        evernoteMainPage.restoreFromTrash(title);
    }


    @Then("^I can see the note with title \"(.*?)\" in the notes list$")
    public void iCanSeeTheNoteWithTitleInTheNotesList(String title) throws Throwable {
        evernoteMainPage.showNotesList();
        Assert.assertTrue(evernoteMainPage.noteWithTitleExists(title));
    }
}
