package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import org.junit.Assert;

import java.util.List;

/**
 * Created by jeanclaude.pace on 02/01/2016.
 */
public class EvernoteTagsSteps {

    public static final String TEST_TAG = "AssignmentTestTag";

    private EvernoteMainPage evernoteMainPage = new EvernoteMainPage();

    @And("^Tag the following notes with the test tag:$")
    public void tagTheFollowingNotesWithTheTestTag(List<String> listOfNoteTitles) throws Throwable {
        for (String current : listOfNoteTitles) {
            evernoteMainPage.tagNote(current,TEST_TAG);
        }
    }

    @Then("^The test tag view contains only the following notes:$")
    public void theTestTagViewContainsOnlyTheFollowingNotes(List<String> listOfNoteTitles) throws Throwable {
        /* We verify that the view contains ONLY the required notes by checking that all the required notes are present
         * and that the sizes of the lists are equal. Hence, it is safe to assume that the list contains nothing "extra"
         */
        evernoteMainPage.selectTag(TEST_TAG);
        List<String> shownNotes = evernoteMainPage.getNotesInList();
        Assert.assertEquals("Size of shown notes in tag is equal to number of notes searched for",listOfNoteTitles.size(),shownNotes.size());
        for (String current : listOfNoteTitles) {
            Assert.assertTrue("List of shown notes contains " + current,shownNotes.contains(current));
        }
    }
}
