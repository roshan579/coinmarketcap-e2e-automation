package com.cmcp.test.pages;

import com.cmcp.test.framework.helpers.WaitHelper;
import com.cmcp.test.framework.hooks.BrowserManagement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Roshan
 */
public class BasePage {

    private static final String BASE_PATH = "src/test/java/";
    public Properties properties;

    public BasePage() {
        try {
            String filename = this.getClass().getName().replaceAll("\\.", "/") + ".properties";
            properties = new Properties();
            FileInputStream input = new FileInputStream(BASE_PATH + filename);
            properties.load(input);
            String baseFileName = BasePage.class.getName().replaceAll("\\.", "/") + ".properties";
            FileInputStream baseInput = new FileInputStream(BASE_PATH + baseFileName);
            properties.load(baseInput);
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public void loadPage() throws Exception {
        int count = 0;
        boolean loaded = false;
        do {
            try {
                WaitHelper.waitUntilExists(this, "main_content");
                WaitHelper.waitUntilVisible(getElement("main_content"));
                WaitHelper.waitUntilExists(this, "section_content");
                WaitHelper.waitUntilVisible(getElement("section_content"));
                WaitHelper.waitUntilExists(this, "currency_details");
                WebElement currencyDetails = getElement("currency_details");
                WaitHelper.waitUntilVisible(currencyDetails);
                List<WebElement> divElements = getWebElements(currencyDetails, "tr", "tagname", false);
                if (divElements.isEmpty()) {
                    loaded = false;
                } else {
                    loaded = true;
                }
            } catch (Exception ex) {
                System.out.println("Loading CoinMarketCap launch page took more time due to : " + ex.getMessage());
                BrowserManagement.closeBrowser();
                BrowserManagement.prepareBrowser();
            }
            count++;
        } while (!loaded && count < WaitHelper.WEBELEMENT_DEFAULT_INPUT_TRY_COUNT);
    }

    public WebElement getElement(String name) throws Exception {
        String[] property = getElementProperty(name);
        WebElement element;
        try {
            element = getWebElement(property[0], property[1], true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(property[0], property[1], true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (Exception ex) {
            throw new Exception("Element is not found in the page : " + name);
        }
        return element;
    }

    public String[] getElementProperty(String name) throws Exception {
        String element[] = new String[2];
        element[0] = getProperty(name);
        element[1] = getProperty(name + "Type");
        if (element[0] != null && element[1] == null) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        return element;
    }

    public WebElement getElement(WebElement parent, String name) throws Exception {
        String elementName = getProperty(name);
        String type = getProperty(name + "Type");
        WebElement element;
        if (StringUtils.isBlank(elementName) && StringUtils.isBlank(type)) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        try {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (Exception ex) {
            throw new Exception("Element is not found in the page : " + name);
        }
        return element;
    }

    public WebElement getElement(WebElement parent, String elementName, String type) throws Exception {
        WebElement element;
        if (StringUtils.isBlank(elementName) && StringUtils.isBlank(type)) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        try {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception("element is null" + elementName);
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception("element is null" + elementName);
            }
        } catch (Exception ex) {
            throw new Exception("Element is not found in the page : " + elementName);
        }
        return element;
    }

    public List<WebElement> getElements(WebElement parent, String name) throws Exception {
        String[] element = getElementProperty(name);
        List<WebElement> elementList;
        try {
            elementList = getWebElements(parent, element[0], element[1], true);
            if (elementList.size() == 0) {
                throw new Exception("element list size is 0 " + name);
            }
        } catch (StaleElementReferenceException sfe) {
            elementList = getWebElements(parent, element[0], element[1], true);
            if (elementList.size() == 0) {
                throw new Exception("element list size is 0 " + name);
            }
        } catch (Exception ex) {
            throw new Exception(String.format("Element %s is not found in the page due to : " + ex.getMessage(), name));
        }
        return elementList;
    }

    public WebElement getWebElement(String property, boolean wait) throws Exception {
        String elementName = getProperty(property);
        String type = getProperty(property + "Type");
        WebElement element = null;
        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = PageObjectFactory.getDriver().findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = PageObjectFactory.getDriver().findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = PageObjectFactory.getDriver().findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = PageObjectFactory.getDriver().findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = PageObjectFactory.getDriver().findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = PageObjectFactory.getDriver().findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = PageObjectFactory.getDriver().findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public WebElement getWebElement(String elementName, String type, boolean wait) throws Exception {
        WebElement element = null;
        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = PageObjectFactory.getDriver().findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = PageObjectFactory.getDriver().findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = PageObjectFactory.getDriver().findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = PageObjectFactory.getDriver().findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = PageObjectFactory.getDriver().findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = PageObjectFactory.getDriver().findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = PageObjectFactory.getDriver().findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public WebElement getWebElement(WebElement parent, String property, boolean wait) throws Exception {
        String elementName = getProperty(property);
        String type = getProperty(property + "Type");
        if (parent == null) {
            return getWebElement(property, wait);
        }
        WebElement element = null;

        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = parent.findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = parent.findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = parent.findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = parent.findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = parent.findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = parent.findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = parent.findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public WebElement getWebElement(WebElement parent, String elementName, String type, boolean wait) throws Exception {
        if (parent == null) {
            return getWebElement(elementName, type, wait);
        }
        WebElement element = null;

        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = parent.findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = parent.findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = parent.findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = parent.findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = parent.findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = parent.findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = parent.findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public List<WebElement> getWebElements(WebElement parent, String property, boolean wait) throws Exception {
        String locator = getProperty(property);
        String type = getProperty(property + "Type");
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        List<WebElement> elementList = new ArrayList<WebElement>();
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    elementList = parent.findElements(By.id(locator));
                } else if (type.equalsIgnoreCase("css")) {
                    elementList = parent.findElements(By.cssSelector(locator));
                } else if (type.equalsIgnoreCase("class")) {
                    elementList = parent.findElements(By.className(locator));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    elementList = parent.findElements(By.partialLinkText(locator));
                } else if (type.equalsIgnoreCase("xpath")) {
                    elementList = parent.findElements(By.xpath(locator));
                } else if (type.equalsIgnoreCase("name")) {
                    elementList = parent.findElements(By.name(locator));
                } else if (type.equalsIgnoreCase("tagname")) {
                    elementList = parent.findElements(By.tagName(locator));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                if (elementList.isEmpty()) {
                    if (!wait) {
                        timeout = true;
                    } else {
                        Thread.sleep(100);
                        time = time - 100;
                    }
                } else {
                    timeout = true;
                }
            } catch (WebDriverException e) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception(String.format("Element %s could not be found in the page : ", locator));
        }
        return elementList;

    }

    public List<WebElement> getWebElements(WebElement parent, String locator, String type, boolean wait)
            throws Exception {
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        List<WebElement> elementList = new ArrayList<WebElement>();
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    elementList = parent.findElements(By.id(locator));
                } else if (type.equalsIgnoreCase("css")) {
                    elementList = parent.findElements(By.cssSelector(locator));
                } else if (type.equalsIgnoreCase("class")) {
                    elementList = parent.findElements(By.className(locator));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    elementList = parent.findElements(By.partialLinkText(locator));
                } else if (type.equalsIgnoreCase("xpath")) {
                    elementList = parent.findElements(By.xpath(locator));
                } else if (type.equalsIgnoreCase("name")) {
                    elementList = parent.findElements(By.name(locator));
                } else if (type.equalsIgnoreCase("tagname")) {
                    elementList = parent.findElements(By.tagName(locator));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                if (elementList.isEmpty()) {
                    if (!wait) {
                        timeout = true;
                    } else {
                        Thread.sleep(100);
                        time = time - 100;
                    }
                } else {
                    timeout = true;
                }
            } catch (WebDriverException e) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception(String.format("Element %s could not be found in the page : ", locator));
        }
        return elementList;

    }

    public void moveFocusOnElement(WebElement element) {
        try {
            new Actions(PageObjectFactory.getDriver()).moveToElement(element).perform();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void scrollDown() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) PageObjectFactory.getDriver();
            js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void scrollIntoView(int height) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) PageObjectFactory.getDriver();
            js.executeScript("window.scrollTo(0, "+height+");");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }





}

