package edu.plas.plas3007.jcpassignment.helper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import edu.plas.plas3007.jcpassignment.driver.Driver;

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

    @Before
    public void deleteAllNotesAfterTests() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                //TODO -- implement logic to remove all created notes
                System.out.println("Removing all created notes. Still TODO");
                //Driver.startWebDriver();
                //Driver.getWebDriver().get("http://40.127.132.250:8090/course/students.jsp");
                //new StudentsHomePage().clickOnListLink();
                //new StudentsListPage().deleteAllStudents();
                //System.out.println("Student deletion complete");
                //if (Driver.getWebDriver() != null) {
                //    Driver.getWebDriver().quit();
                //    Driver.nullWebDriver();
                //}
            }
        });
    }

}
