package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import org.junit.Assert;

/**
 * Created by jeanclaude.pace on 31/12/2015.
 */
public class EvernoteSearchSteps {

    private EvernoteMainPage evernoteMainPage = new EvernoteMainPage();

    @And("^I search for \"(.*?)\"$")
    public void iSearchFor(String searchString) throws Throwable {
        evernoteMainPage.searchForText(searchString);
    }

    @Then("^I can see the note with title \"(.*?)\"$")
    public void iCanSeeTheNoteWithTitle(String noteTitle) throws Throwable {
        Assert.assertEquals(noteTitle,evernoteMainPage.getCurrentNoteTitle());
    }
}
