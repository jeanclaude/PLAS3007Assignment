package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteLoginPage;
import org.junit.Assert;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class EvernoteLoginSteps {

    private static final String validUsername="jean-claude.pace.99@um.edu.mt";
    private static final String validPassword="jeanP1A5";
    private static final String invalidUsername="invalid@inexistant.com";
    private static final String invalidPassword="blablabla";

    private EvernoteLoginPage evernoteLoginPage = new EvernoteLoginPage();

    @When("^I log into Evernote with (.*) credentials$")
    public void iLogIntoEvernoteWithCorrectCredentials(String credentialsType) throws Throwable {
        switch (credentialsType) {
            case "correct":
                //todo make these customisable
                evernoteLoginPage.populateUsername(validUsername);
                evernoteLoginPage.populatePassword(validPassword);
                break;
            case "incorrect":
                evernoteLoginPage.populateUsername(invalidUsername);
                evernoteLoginPage.populatePassword(invalidPassword);
                break;
            case "no":
                evernoteLoginPage.populateUsername("");
                evernoteLoginPage.populatePassword("");
                break;

            default:
                throw new PendingException("Credentials type " + credentialsType + " has not been implemented");
        }
        evernoteLoginPage.clickLoginButton();
    }


    @Then("^The user is logged in$")
    public void theUserIsLoggedIn() throws Throwable {
        Assert.assertTrue(evernoteLoginPage.userIsLoggedIn(validUsername));
    }

    @Then("^An error indicating \"(.*?)\" is shown$")
    public void anErrorIndicatingIsShown(String errorType) throws Throwable {
        String shownErrorMessage=evernoteLoginPage.getLoginPageErrorMessage();
        switch (errorType) {
            case "No Credentials":
                Assert.assertTrue(shownErrorMessage.toUpperCase().contains("THIS IS A REQUIRED FIELD"));
                break;
            case "Incorrect Credentials":
                Assert.assertTrue(shownErrorMessage.toUpperCase().contains("INCORRECT USERNAME AND/OR PASSWORD"));
                break;
            default:
                throw new PendingException("Error type " + errorType + " has not been implemented");
        }
    }
}
