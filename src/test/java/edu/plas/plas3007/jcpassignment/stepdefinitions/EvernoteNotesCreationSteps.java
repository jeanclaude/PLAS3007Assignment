package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import org.junit.Assert;

import java.util.List;
import java.util.Objects;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class EvernoteNotesCreationSteps {

    private final String TEST_TITLE = "Note Created by Auto Test";
    private final String TEST_DESCRIPTION = "Test description created automatically";

    private final String SORTING_TEST_NOTE_1 = "BB - First Note Created";
    private final String SORTING_TEST_NOTE_2 = "CC - Second Note Created";
    private final String SORTING_TEST_NOTE_3 = "AA - Third Note Created";

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

    @When("^I create a new note with a (\\d+)x(\\d+) table inside it$")
    public void iCreateANewNoteWithAXTableInsideIt(int tableWidth, int tableHeight) throws Throwable {
        evernoteMainPage.createNewNoteWithTableInsideIt(TEST_TITLE,TEST_DESCRIPTION,tableWidth,tableHeight);
    }

    @And("^The selected note has a (\\d+)x(\\d+) table inside it$")
    public void theSelectedNoteHasAXTableInsideIt(int tableWidth, int tableHeight) throws Throwable {
        Assert.assertTrue(evernoteMainPage.checkNoteHasTableInsideIt(TEST_TITLE,tableWidth,tableHeight));
    }

    @When("^I create some test notes for sorting$")
    public void iCreateSomeTestNotesForSorting() throws Throwable {
        evernoteMainPage.createNewNote(SORTING_TEST_NOTE_1,"Description" + SORTING_TEST_NOTE_1);
        evernoteMainPage.createNewNote(SORTING_TEST_NOTE_2,"Description" + SORTING_TEST_NOTE_2);
        evernoteMainPage.createNewNote(SORTING_TEST_NOTE_3,"Description" + SORTING_TEST_NOTE_3);
    }

    @Then("^The notes are sorted correctly by \"(.*?)\"$")
    public void theNotesAreSortedCorrectlyBy(String sortOrder) throws Throwable {
        List<String> sortedNotes = evernoteMainPage.getNotesBySortOrder(sortOrder);
        switch (sortOrder) {
            case "Date Created (oldest first)":
                Assert.assertTrue(sortedNotes.get(0).equals(SORTING_TEST_NOTE_1) &&
                                Objects.equals(sortedNotes.get(1), SORTING_TEST_NOTE_2) &&
                                Objects.equals(sortedNotes.get(2), SORTING_TEST_NOTE_3)
                                 );
                break;
            case "Date Created (newest first)":
                Assert.assertTrue(Objects.equals(sortedNotes.get(0), SORTING_TEST_NOTE_3) &&
                        Objects.equals(sortedNotes.get(1), SORTING_TEST_NOTE_2) &&
                        Objects.equals(sortedNotes.get(2), SORTING_TEST_NOTE_1)
                );
                break;
            case "Title (ascending)":
                Assert.assertTrue(Objects.equals(sortedNotes.get(0), SORTING_TEST_NOTE_3) &&
                        Objects.equals(sortedNotes.get(1), SORTING_TEST_NOTE_1) &&
                        Objects.equals(sortedNotes.get(2), SORTING_TEST_NOTE_2)
                );
                break;
            case "Title (descending)":
                Assert.assertTrue(Objects.equals(sortedNotes.get(0), SORTING_TEST_NOTE_2) &&
                        Objects.equals(sortedNotes.get(1), SORTING_TEST_NOTE_1) &&
                        Objects.equals(sortedNotes.get(2), SORTING_TEST_NOTE_3)
                );
                break;
            default:
                System.out.println("Invalid sort order passed");
                throw new PendingException();
        }
    }
}

