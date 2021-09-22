package com.cmcp.test.framework.hooks;

import com.cmcp.test.framework.helpers.PropertyHelper;
import com.cmcp.test.pages.PageObjectFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class BrowserManagement {

    public static WebDriver driver;
    public static Scenario featureScenario;
    public static String downLoadDirectory;
    public static String applicationURL;


    @Before(order = 1)
    public static void readConfigurationDetails(Scenario scenario) {
        try {
            String host = System.getProperty("environment.host");
            if (StringUtils.isBlank(host)) {
                host = PropertyHelper.getProperty("local.host").toLowerCase();
            }
            applicationURL = host;
            System.out.println("App URL Generated:\n" + applicationURL);
            featureScenario = scenario;
            String dPath = System.getProperty("environment.downloadpath");
            if (StringUtils.isBlank(dPath)) {
                dPath = PropertyHelper.getProperty("local.downloadpath").toLowerCase();
            }
            downLoadDirectory = System.getProperty("user.dir") + dPath;
            File downloadPath = new File(downLoadDirectory);
            if (downloadPath.exists()) {
                FileUtils.forceDelete(downloadPath);
                Thread.sleep(100);
                downloadPath.mkdir();
            } else {
                downloadPath.mkdir();
            }
        } catch (Exception ex) {
            throw new Error("Environment details are improper due to : " + ex.getMessage());
        }
    }

    @Before(value = "~@NoBrowser", order = 2)
    public static void prepareBrowser() {
        try {
            downLoadDirectory = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/src/test/resources/download";
            File downloadPath = new File(downLoadDirectory);
            if (downloadPath.exists()) {
                FileUtils.forceDelete(downloadPath);
                Thread.sleep(100);
                downloadPath.mkdir();
            } else {
                downloadPath.mkdir();
            }
            driver = getWebDriver();
            driver.navigate().to(applicationURL);
            System.out.println("App URL Opened in Browser:\n" + applicationURL);
            PageObjectFactory.setDriver(driver);
            try {
                driver.manage().window().maximize();
            } catch (WebDriverException ex) {
                System.out.println("This version of driver cannot maximise the browser due to : " + ex.getMessage());
            }
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        } catch (Exception ex) {
            System.out.println("could not open browser due to : \n" + ex.getMessage());
        }
    }

    @After(value = "~@NoBrowser", order = 101)
    public static void updateScreenShot(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png", scenario.getName());
            }
        } catch (Exception ex) {
            System.out.println("Error in taking the screen shot due to : " + ex.getMessage());
        }
    }

    @After(value = "~@NoBrowser", order = 99)
    public static void closeBrowser() {
        try {
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle).close();
            }
        } catch (Exception ex) {
            System.out.println("There was an error thrown while driver quit call \n" + ex.getMessage());
        }
    }


    public static WebDriver getWebDriver() throws Exception {
        WebDriver driver;
        String browserType = PropertyHelper.getProperty("BrowserType");
        if (browserType.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", PropertyHelper.getProperty("chromeDriver"));
            ChromeOptions options = new ChromeOptions();
            options.addArguments("disable-extensions");
            options.addArguments("disable-infobars");
            options.addArguments("start-maximized");
            //options.addArguments("headless");
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downLoadDirectory);
            options.setExperimentalOption("prefs", chromePrefs);
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else if (browserType.equalsIgnoreCase("opera")) {
            driver = new OperaDriver();
        } else if (browserType.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if (browserType.equalsIgnoreCase("IE")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new Exception("browser type is not defined");
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return driver;
    }



}

