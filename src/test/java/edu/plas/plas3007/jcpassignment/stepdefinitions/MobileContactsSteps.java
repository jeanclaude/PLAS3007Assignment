package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.domainobjects.AndroidContact;
import edu.plas.plas3007.jcpassignment.driver.Driver;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.AndroidContactsPage;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by jeanclaude.pace on 06/01/2016.
 */
public class MobileContactsSteps {

    private AndroidDriver driver = (AndroidDriver) Driver.getWebDriver();
    private AndroidContactsPage androidContactsPage = new AndroidContactsPage();


    @Given("^I log into the Contacts app$")
    public void iLogIntoTheContactsApp() throws Throwable {
        //todo move into a page object model class
        // Write code here that turns the phrase above into concrete actions
//        driver.launchApp();
//        driver.findElementByAndroidUIAutomator("People").click();;
        //driver.findElement(By.xpath("//android.widget.Button[@text='Create a new contact']")).click();
        //driver.findElement(By.xpath("//android.widget.EditText[@text='Name']")).sendKeys("Test Contact");


        //By.id("com.android.id/sorting_action_button");
        //By.xpath("//android.widget.EditText[@text='Name']")

    }


    @When("^I create a new contact with name \"(.*?)\", mobile number \"(.*?)\", home number \"(.*?)\" and email address \"(.*?)\"$")
    public void iCreateANewContactWithNameMobileNumberHomeNumberAndEmailAddress(String contactName, String contactMobileNumber, String contactHomeNumber, String contactEmailAddress) throws Throwable {
        androidContactsPage.createContactWhenThereAreNoContacts(contactName,contactMobileNumber,contactHomeNumber,contactEmailAddress);
    }

    @Then("^The displayed contact has name \"(.*?)\", mobile number \"(.*?)\", home number \"(.*?)\" and email address \"(.*?)\"$")
    public void theDisplayedContactHasNameMobileNumberHomeNumberAndEmailAddress(String contactName, String contactMobile, String contactHome, String contactEmail) throws Throwable {
        AndroidContact displayedContact = androidContactsPage.getDisplayedContact();
        Assert.assertEquals("Checking displayed Name",contactName,displayedContact.getName());
        Assert.assertEquals("Checking displayed Mobile Number",contactMobile,displayedContact.getMobileNumber());
        Assert.assertEquals("Checking displayed Home Number",contactHome,displayedContact.getFixedNumber());
        Assert.assertEquals("Checking displayed Email",contactEmail,displayedContact.getEmailAddress());
    }

    @When("^I return from the contact to the address book$")
    public void iReturnFromTheContactToTheAddressBook() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        androidContactsPage.exitFromContactPage();
        androidContactsPage.goToAllContactsView();
    }

    @Then("^There is a contact with name \"(.*?)\"$")
    public void thereIsAContactWithName(String contact) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue("Checking that the contact " + contact + " is present on the page", androidContactsPage.contactIsPresentOnPage(contact));
    }

    @When("^I delete all contacts$")
    public void iDeleteAllContacts() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        androidContactsPage.deleteAllContacts();

    }

    @And("^I select the contact with name \"(.*?)\"$")
    public void iSelectTheContactWithName(String contactName) throws Throwable {
        androidContactsPage.selectContact(contactName);
    }

    @And("^I change the name and mobile number of the selected contact to \"(.*?)\" and \"(.*?)\"$")
    public void iChangeTheNameAndMobileNumberOfTheSelectedContactToAnd(String contactName, String contactMobile) throws Throwable {
        androidContactsPage.editContact(contactName,contactMobile);
    }

    @And("^I flag the displayed contact as favourite$")
    public void iFlagTheDisplayedContactAsFavourite() throws Throwable {
        androidContactsPage.flagContactAsFavourite();
    }

    @And("^I go to the favourites view$")
    public void iGoToTheFavouritesView() throws Throwable {
        androidContactsPage.goToFavouritesView();
    }
}
