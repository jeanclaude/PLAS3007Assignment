package edu.plas.plas3007.jcpassignment.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by jeanclaude.pace on 26/12/2015.
 */
public class Driver {
    private static WebDriver driver = null;
    private static String browser;
    private static final String SELENIUM_GRID_HUB_URL = "http://localhost:4444/wd/hub";
    private static final String CHROME_DRIVER_MAC_PATH = "browserdriver/chrome/chromedriver";
    private static final String CHROME_DRIVER_WINDOWS_PATH = "browserdriver/chrome/chromedriver.exe";
    private static final String IE_DRIVER_WINDOWS_PATH = "browserdriver/ie/IEDriverServer.exe";
    private static final String APPIUM_DRIVER_PATH = "http://127.0.0.1:4723/wd/hub";

    private Driver() {}

    public static WebDriver getWebDriver() {
        if (driver == null) {
            throw new IllegalStateException("Selenium WebDriver is not initialised!");
        }
        return driver;
    }

    public static void startWebDriver() {
        // Check whether driver has already been initialised
        if (driver != null) {
            throw new IllegalStateException("Selenium WebDriver has already been initialised!");
        }
        System.out.println("Browser parameter = " + browser);
        try {
            switch (browser) {
                case "localFirefox":
                    FirefoxProfile firefoxProfile = new FirefoxProfile();
                    firefoxProfile.setEnableNativeEvents(false);
                    driver = new FirefoxDriver(firefoxProfile);
                    break;

                case "localChrome":
                    if (System.getProperty("os.name").contains("Mac")) {
                        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_MAC_PATH);
                    } else if (System.getProperty("os.name").contains("Windows")) {
                        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_WINDOWS_PATH);
                    }
                    driver = new ChromeDriver();
                    break;

                case "localIE":
                    System.setProperty("webdriver.ie.driver", IE_DRIVER_WINDOWS_PATH);
                    driver = new InternetExplorerDriver();
                    break;

                case "gridFirefox":
                    FirefoxProfile firefoxProfileGrid = new FirefoxProfile();
                    firefoxProfileGrid.setEnableNativeEvents(false);
                    DesiredCapabilities firefoxCapability = DesiredCapabilities.firefox();
                    firefoxCapability.setBrowserName("firefox");
                    firefoxCapability.setVersion("43.0.4");
                    firefoxCapability.setPlatform(Platform.WINDOWS);
                    firefoxCapability.setCapability(FirefoxDriver.PROFILE, firefoxProfileGrid);
                    driver = new RemoteWebDriver(new URL(SELENIUM_GRID_HUB_URL), firefoxCapability);
                    break;

                case "gridChrome":
                    DesiredCapabilities chromeCapability = DesiredCapabilities.chrome();
                    chromeCapability.setBrowserName("chrome");
                    chromeCapability.setPlatform(Platform.WINDOWS);
                    driver = new RemoteWebDriver(new URL(SELENIUM_GRID_HUB_URL), chromeCapability);
                    break;

                case "gridIE":
                    DesiredCapabilities ieCapability = DesiredCapabilities.internetExplorer();
                    ieCapability.setBrowserName("internet explorer");
                    ieCapability.setVersion("11");
                    ieCapability.setPlatform(Platform.WINDOWS);
                    driver = new RemoteWebDriver(new URL(SELENIUM_GRID_HUB_URL), ieCapability);
                    break;

                case "appium":
                    DesiredCapabilities appiumCapability = DesiredCapabilities.android();

                    //automationName : Appium
                    //newCommandTimeout : 30
                    // platformName : Android
                    // platformVersion : 5.1
                    // deviceReadyTimeout : 30


                    appiumCapability.setCapability(MobileCapabilityType.BROWSER_NAME, "");
                    appiumCapability.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
                    appiumCapability.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                    //appiumCapability.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, 30000);
                    //appiumCapability.setCapability(MobileCapabilityType.APP,"Contacts");
                    //appiumCapability.setCapability(MobileCapabilityType.APP_PACKAGE,"com.android.contacts.ContactsApplication");
                    appiumCapability.setCapability(MobileCapabilityType.APP_PACKAGE,"com.android.contacts");
                    appiumCapability.setCapability(MobileCapabilityType.APP_ACTIVITY,".DialtactsContactsEntryActivity");
                    //appiumCapability.setCapability(MobileCapabilityType.APP_ACTIVITY,".activities.PeopleActivity");
                    driver = new AndroidDriver(new URL(APPIUM_DRIVER_PATH),appiumCapability);
                    break;

                default:
                    throw new IllegalArgumentException("Browser system property is wrong! Cannot be " + browser);
            }

            if (!browser.equals("appium")) {
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                driver.manage().window().maximize();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Selenium WebDriver!", e);
        }
    }

    public static String getBrowser() {
        return browser;
    }

    public static void setBrowser(String browser) {
        Driver.browser = browser;
    }

    public static void nullWebDriver() {
        driver = null;
    }
}
