package edu.plas.plas3007.jcpassignment.helper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import edu.plas.plas3007.jcpassignment.driver.Driver;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.AndroidContactsPage;
import edu.plas.plas3007.jcpassignment.pageobjectmodels.EvernoteMainPage;
import edu.plas.plas3007.jcpassignment.stepdefinitions.EvernoteLoginSteps;
import edu.plas.plas3007.jcpassignment.stepdefinitions.EvernoteTagsSteps;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class CucumberBeforeAfter {
    private static boolean imagesCleaned = false;

    // This ensures that this @Before is always executed first
    @Before(order = 0)
    public void setup() {
        // Delete all screen shots from previous execution
        // THIS SHOULD BE EXECUTED ONLY ONCE
        if (!imagesCleaned) {
            File reportsDirectory = new File("reports/html-reports");
            final File[] files = reportsDirectory.listFiles((dir, name) -> {
                return name.matches(".*.jpeg");
            });
            for (final File file : files) {
                file.delete();
            }
            imagesCleaned = true;
        }

        Driver.setBrowser(System.getProperty("browser"));
        Driver.startWebDriver();
    }

    // This ensures that this @After is always executed last
    @After(order = 0)
    public void tearDown(Scenario scenario) {
        // If Cucumber scenario fails, output time of failure and take screen shot
        if (scenario.isFailed()) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            scenario.write("Time of failure: " + dateFormat.format(Calendar.getInstance().getTime()));
            DriverScreenShotHelper.takeScreenShot(scenario);
        }

        if (Driver.getWebDriver() != null) {
            Driver.getWebDriver().quit();
            Driver.nullWebDriver();
        }
    }

    @After(order = 1000, value = "@DeleteTestTag")
    public void deleteTestTag() {
        EvernoteMainPage evernoteMainPage = new EvernoteMainPage();
        evernoteMainPage.deleteTag(EvernoteTagsSteps.TEST_TAG);
    }

    @After(order = 100, value = "@DeleteAllNotebooksWhenDone")
    public void deleteAllNotebooks() {
        EvernoteMainPage evernoteMainPage = new EvernoteMainPage();
        evernoteMainPage.deleteAllNotebooksExceptDefault();
    }

    @After(value = "@DeleteNotesWhenDone")
    public void deleteAllNotesAfterTests() {
        EvernoteMainPage evernoteMainPage = new EvernoteMainPage();
        evernoteMainPage.deleteAllNotes();
    }

//    @Before(value = "@DeleteNotesWhenDone")
    public void deleteAllNotesAfterAllTestsAreDone() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Removing all created notes.");
                try {
                    Driver.startWebDriver();
                    Driver.getWebDriver().get("https://www.evernote.com/Login.action");
                    EvernoteLoginSteps evernoteLoginSteps=new EvernoteLoginSteps();
                    evernoteLoginSteps.iAmLoggedIntoEvernote();
                    EvernoteMainPage evernoteMainPage = new EvernoteMainPage();
                    evernoteMainPage.deleteAllNotes();
                    System.out.println("Note deletion complete");
                    if (Driver.getWebDriver() != null) {
                        Driver.getWebDriver().quit();
                        Driver.nullWebDriver();
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    @After(value = "@Android")
    public void deleteAllAndroidContacts() {
        AndroidContactsPage androidContactsPage = new AndroidContactsPage();
        androidContactsPage.deleteAllContacts();
    }
}
