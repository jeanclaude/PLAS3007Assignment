package edu.plas.plas3007.jcpassignment.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/edu/plas/plas3007/jcpassignment/features/",
        glue = "edu.plas.testautoandci",
        plugin = {"pretty", "junit:reports/junit.xml", "json:reports/cucumber.json", "html:reports/html-reports"},
        monochrome = true)
public class RunCukesTest {
}
