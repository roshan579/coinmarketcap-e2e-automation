package com.cmcp.test.pages;

import com.cmcp.test.pages.backEnd.BackEndAPIPage;
import com.cmcp.test.pages.frontEnd.CoinMarketCapHomePage;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roshan
 */
public class PageObjectFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }

    public static CoinMarketCapHomePage getCoinMarketCapHomePage() {
        return new CoinMarketCapHomePage();
    }

    public static BackEndAPIPage getBackEndAPIPage() {
        return new BackEndAPIPage();
    }

}
