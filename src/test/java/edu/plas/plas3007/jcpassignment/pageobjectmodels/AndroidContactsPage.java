package edu.plas.plas3007.jcpassignment.pageobjectmodels;

import edu.plas.plas3007.jcpassignment.domainobjects.AndroidContact;
import edu.plas.plas3007.jcpassignment.driver.Driver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by JeanClaude.Pace on 07/01/2016.
 */
public class AndroidContactsPage {

    private WebDriver driver = Driver.getWebDriver();

    public void createContactWhenThereAreNoContacts(String contactName, String contactMobileNumber, String contactHomeNumber, String contactEmailAddress) {
        driver.findElement(By.xpath("//android.widget.Button[@text='Create a new contact']")).click();
        driver.findElement(By.xpath("//android.widget.EditText[@text='Name']")).sendKeys(contactName);
        driver.findElement(By.xpath("//android.widget.EditText[@text='Phone']")).sendKeys(contactMobileNumber);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Add new']")).click();
        try { Thread.sleep(1000); } catch (Exception e) {}
        driver.findElement(By.xpath("//android.widget.EditText[@text='Phone']")).sendKeys(contactHomeNumber);
        driver.findElement(By.xpath("//android.widget.EditText[@text='Email']")).sendKeys(contactEmailAddress);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Done']")).click();
    }

    public AndroidContact getDisplayedContact() {
        AndroidContact returnContact=new AndroidContact();
        //String name = driver.findElement(By.xpath("//*[@content-desc='Navigate up']")).findElement(By.className("android.widget.LinearLayout"))
        //        .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView")).getText();
        String name = driver.findElements(By.className("android.widget.LinearLayout")).get(1).findElement(By.className("android.widget.LinearLayout"))
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView")).getText();
        System.out.println("Returned Android Contact Name is " + name);
        returnContact.setName(name);

        String mobile = driver.findElement(By.className("android.support.v4.view.ViewPager")).findElement(By.className("android.widget.FrameLayout"))
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.ListView"))
                .findElements(By.className("android.widget.FrameLayout")).get(1)
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.LinearLayout"))
                .findElement(By.className("android.widget.TextView")).getText();
        System.out.println("Found Android Contact Mobile Number is " + mobile);
        mobile=mobile.replaceAll("\\D+","");  //cleaning out the non-digits since the Contacts sometimes changes the phone numbers to strings like (123) 456-789
        System.out.println("Returning Android Contact Mobile Number is " + mobile);
        returnContact.setMobileNumber(mobile);

        String fixed = driver.findElement(By.className("android.support.v4.view.ViewPager")).findElement(By.className("android.widget.FrameLayout"))
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.ListView"))
                .findElements(By.className("android.widget.FrameLayout")).get(3)
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.LinearLayout"))
                .findElement(By.className("android.widget.TextView")).getText();
        System.out.println("Found Android Contact Fixed Number is " + fixed);
        fixed=fixed.replaceAll("\\D+","");  //cleaning out the non-digits since the Contacts sometimes changes the phone numbers to strings like (123) 456-789
        System.out.println("Returning Android Contact Fixed Number is " + fixed);
        returnContact.setFixedNumber(fixed);

        String email = driver.findElement(By.className("android.support.v4.view.ViewPager")).findElement(By.className("android.widget.FrameLayout"))
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.ListView"))
                .findElements(By.className("android.widget.FrameLayout")).get(6)
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.LinearLayout"))
                .findElement(By.className("android.widget.TextView")).getText();
        System.out.println("Returned Android Contact Email is " + email);
        returnContact.setEmailAddress(email);

        return returnContact;
    }

    public void exitFromContactPage() {
        driver.findElements(By.className("android.widget.LinearLayout")).get(1).findElement(By.className("android.widget.LinearLayout"))
                .findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView")).click();
    }

    public void goToAllContactsView() {
        try {Thread.sleep(1000);} catch (Exception e) {}
        driver.findElement(By.xpath("//*[@content-desc='All contacts']")).click();
        try {Thread.sleep(1000);} catch (Exception e) {}
    }

    public void goToFavouritesView() {
        try {Thread.sleep(1000);} catch (Exception e) {}
        driver.findElement(By.xpath("//*[@content-desc='Favorites']")).click();
        try {Thread.sleep(1000);} catch (Exception e) {}
    }

    public boolean contactIsPresentOnPage(String contactName) {
        try {
            System.out.println("Searching for  " + contactName);
            WebElement foundElement = driver.findElement((By.xpath("//android.widget.TextView[@text='" + contactName + "']")));
            System.out.println("Found: " + foundElement.getText());
            return contactName.equals(foundElement.getText());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Did not find " + contactName);
            return false;
        }

    }

    /**
     * This method assumes that we are in the Contacts page
     */
    public void deleteAllContacts(){
        List<WebElement> foundContacts;
        while ((foundContacts = driver.findElements(By.className("android.view.View"))).size()>0) {
            WebElement current=foundContacts.get(0);
            current.click();
            try {Thread.sleep(2000);} catch (Exception e) {}
            //driver.findElement(By.className("android.widget.ImageButton")).click();
            driver.findElement(By.xpath("//*[@content-desc='More options']")).click();
            try {Thread.sleep(2000);} catch (Exception e) {}
            driver.findElement(By.xpath("//android.widget.TextView[@text='Delete']")).click();
            try {Thread.sleep(2000);} catch (Exception e) {}
            driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }
    }

    /**
     * This method assumes that we are in the Contacts page
     */
    public void selectContact(String contactName) {
        try {
            System.out.println("Trying to select  " + contactName);
            WebElement foundElement = driver.findElement((By.xpath("//android.widget.TextView[@text='" + contactName + "']")));
            System.out.println("Found: " + foundElement.getText());
            foundElement.click();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Did not find " + contactName);
        }

    }

    /*
    This method assumes that we are in the Contact Details page
    The method only allows the editting of contact name and mobile number because that is all that is required at this point in time
     */
    public void editContact(String contactName, String contactMobileNumber) {
        try {Thread.sleep(2000);} catch (Exception e) {}
        //driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.findElement(By.xpath("//*[@content-desc='More options']")).click();
        try {Thread.sleep(2000);} catch (Exception e) {}
        driver.findElement(By.xpath("//android.widget.TextView[@text='Edit']")).click();
        try {Thread.sleep(2000);} catch (Exception e) {}
        List<WebElement> editTextFields = driver.findElements(By.className("android.widget.EditText"));
        editTextFields.get(0).sendKeys(contactName);
        editTextFields.get(1).sendKeys(contactMobileNumber);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Done']")).click();
    }

    /*
     * This method assumes that we are in the Contact details page
     */
    public void flagContactAsFavourite() {
        try {Thread.sleep(1000);} catch (Exception e) {}
        driver.findElement(By.xpath("//*[@content-desc='Add to favorites']")).click();
        try {Thread.sleep(1000);} catch (Exception e) {}
    }

}
