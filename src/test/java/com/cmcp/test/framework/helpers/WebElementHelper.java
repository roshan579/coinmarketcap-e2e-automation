package com.cmcp.test.framework.helpers;

import com.cmcp.test.pages.PageObjectFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.openqa.selenium.Keys.TAB;

/**
 * Created by Roshan
 */
public class WebElementHelper {

    public static final int WEBELEMENT_DEFAULT_TIMEOUT = 20;

    public static void enterText(WebElement element, String value) throws Exception {
        moveFocusOnElement(element);
        clickElement(element);
        element.clear();
        element.sendKeys(value);
        element.sendKeys(TAB);
    }

    public static void clickElement(WebElement element) throws Exception {
        int seconds = WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                element.click();
                timeout = true;
                Thread.sleep(100);
            } catch (Exception ex) {
                timeout = false;
                Thread.sleep(100);
                time = time - 100;
            }
        }
        if (!timeout) {
            throw new Exception("Element not clickable at the moment");
        }
    }

    public static String getText(WebElement element) throws Exception {
        int seconds = WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        String text = null;
        while (!timeout && time > 0) {
            try {
                text = element.getText();
                if (!StringUtils.isEmpty(text)) {
                    Thread.sleep(100);
                    timeout = true;
                }
            } catch (Exception ex) {
                timeout = false;
                Thread.sleep(100);
            }
            time = time - 100;
        }
        if (!timeout) {
            throw new Exception("Failed to retrieve text from the element : " + element);
        }
        return text;
    }

    public static void moveFocusOnElement(WebElement element) {
        try {
            new Actions(PageObjectFactory.getDriver()).moveToElement(element).perform();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public static void clickElementUsingJQuery(WebElement element) throws Exception{
        try {
            moveFocusOnElement(element);
            JavascriptExecutor js = (JavascriptExecutor) PageObjectFactory.getDriver();
            js.executeScript("arguments[0].click();", element);
        } catch (Exception ex) {
            throw new Exception("could not click on element due to : "+ex.getMessage());
        }
    }



}
