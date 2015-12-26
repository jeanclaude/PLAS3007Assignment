package edu.plas.plas3007.jcpassignment.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import edu.plas.plas3007.jcpassignment.driver.Driver;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class NavigationSteps {

    @Given("^I navigate to \"(.*)\"$")
    public void iNavigateTo(String site) {
        Driver.getWebDriver().get(site);
    }

}
