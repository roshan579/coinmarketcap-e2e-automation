package com.cmcp.test.framework.helpers;

import com.cmcp.test.pages.BasePage;
import com.cmcp.test.pages.PageObjectFactory;
import com.google.common.base.Stopwatch;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Roshan
 */
public class WaitHelper {

    public static final int WEBELEMENT_DEFAULT_TIMEOUT = 30;
    public static final int WEBELEMENT_DEFAULT_INPUT_TRY_COUNT = 3;
    public static final int WEBELEMENT_REFRESH_TIMEOUT = 5;


    public static void waitUntilVisible(WebElement element) {
        try {
            if (element != null) {
                WebDriver driver = PageObjectFactory.getDriver();
                WebDriverWait wait = new WebDriverWait(driver, WEBELEMENT_DEFAULT_TIMEOUT);
                wait.until(ExpectedConditions.visibilityOf(element));
            }
        } catch (Exception ex) {
            System.out.println("wait until visible " + element.getAttribute("name") + " : failed more than "
                    + WEBELEMENT_DEFAULT_TIMEOUT + " seconds");
        }
    }

    public static void waitUntilExists(BasePage basePage, String propertyName) throws Exception {
        Stopwatch timer = Stopwatch.createUnstarted();
        Boolean timeout = false;
        WebElement element = null;
        timer.start();
        do {
            try {
                element = basePage.getWebElement(propertyName, false);
            } catch (Exception ex) {
            }
            if (timer.toString().contains("min")) {
                if (Integer.parseInt(timer.toString().split("\\W+")[0]) >= 1) {
                    timeout = true;
                }
            }
        } while (!timeout && element == null);
        timer.stop();
        if (element == null) {
            throw new Exception("wait until exists " + propertyName + " : failed more than " + timer);
        }
    }


    public static void waitUntilElementRefreshed(WebElement webElement){
        try {
            if(webElement != null){
                WebDriver driver = PageObjectFactory.getDriver();
                WebDriverWait wait = new WebDriverWait(driver, WEBELEMENT_REFRESH_TIMEOUT);
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeSelected(webElement)));
            }
        }catch (Exception ex){

        }
    }

}
