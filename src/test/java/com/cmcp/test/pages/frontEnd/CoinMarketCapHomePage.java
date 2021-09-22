package com.cmcp.test.pages.frontEnd;


import static com.cmcp.test.framework.helpers.WaitHelper.*;
import static com.cmcp.test.framework.helpers.WebElementHelper.*;

import com.cmcp.test.framework.context.frontEnd.FrontEndTasksContext;
import com.cmcp.test.pages.BasePage;
import com.cmcp.test.pages.PageObjectFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


/**
 * Created by Roshan
 *
 * This is a Page Object Model CoinMarketCapHomePage methods for FrontEndTasks Feature implementation
 *
 */

public class CoinMarketCapHomePage extends BasePage {


    public CoinMarketCapHomePage() {
        PageFactory.initElements(PageObjectFactory.getDriver(), this);
    }



    @FindBy(how = How.CSS, css = "div[class^='cmc-cookie-policy-banner__close']")
    public WebElement cookieBannerCloseElement;

    @FindBy(how = How.CSS, css = "div[class^='sc-16r8icm-0 ekMmID table-control-page-sizer'] > div > svg")
    public WebElement showRowsDropDownElement;

    @FindBy(how = How.CSS, css = "div[class^='sc-16r8icm-0 sc-1f0grbq-0 cEoyCq']")
    public WebElement showRowsDropDownValuesElement;

    @FindBy(how = How.CSS, css = "div[class^='sc-16r8icm-0 sc-1f0grbq-0 cEoyCq'] > button")
    public List<WebElement> noOfRowsButtonElements;

    @FindBy(how = How.CSS, css = "button[class^='x0o17e-0 ewuQaX sc-36mytl-0 bBafzO table-control-filter']")
    public WebElement filtersButtonElement;

    @FindBy(how = How.CSS, css = "ul[class^='sc-1erqz0q-0 kSFTVn']")
    public WebElement filtersIconElement;

    @FindBy(how = How.CSS, css = "ul[class^='sc-1erqz0q-0 kSFTVn'] > li")
    public List<WebElement> filterIconElements;

    @FindBy(how = How.CSS, css = "div[class^='popup-content']")
    public WebElement filterPopupElement;

    @FindBy(how = How.CSS, css = "button[data-qa-id^='filter-dd-button-apply']")
    public WebElement applyFilterButton;

    @FindBy(how = How.CSS, css = "button[class^='x0o17e-0 lgnbfA cmc-filter-button']")
    public WebElement showResultsButton;



    /**
     * default browser launch page to open the given website from config file properties
     *
     * @return
     */
    public void userShouldLaunchTheCoinMarketCapWebsite() throws Exception{
        loadPage();
        waitUntilVisible(cookieBannerCloseElement);
        clickElement(cookieBannerCloseElement);
    }

    /**
     * click on show rows dropdown and select the noOfRows
     *
     * @return
     */
    public void clickOnDropDownAndSelectRows(String dropDownType, String noOfRows) throws Exception{
        try {
            if(!StringUtils.isBlank(dropDownType) && !StringUtils.isBlank(noOfRows)){
                if(dropDownType.equalsIgnoreCase("Show Rows")){
                    moveFocusOnElement(showRowsDropDownElement);
                    clickElement(showRowsDropDownElement);
                }
                waitUntilVisible(showRowsDropDownValuesElement);
                for(WebElement buttonElement : noOfRowsButtonElements){
                    moveFocusOnElement(buttonElement);
                    if(getText(buttonElement).equalsIgnoreCase(noOfRows)){
                        clickElement(buttonElement);
                        break;
                    }
                }
            }else{
                Assert.assertFalse("dropdwon type and no of rows are not defined", true);
            }
        } catch (Exception ex) {
            Assert.assertFalse("selectDropDownValueTo dropdownType : "+dropDownType+" with rows : "+noOfRows+
                    " failed due to : " + ex.getMessage(), true);
        }
    }

    /**
     * verify the no of rows displayed by retriveing ui displayed rows and comparing with given row count
     *
     * @return
     */
    public void verifyThatRowsAreDisplayed(String noOfRows) throws Exception{
        try {
            if(!StringUtils.isBlank(noOfRows)){
                WebElement tableBodyElement = getElement("currency_details");
                waitUntilElementRefreshed(tableBodyElement);
                List<WebElement> tableRowsElements = getElements(tableBodyElement, "tr_tagname");
                Assert.assertFalse("Rows size displayed in UI : "+tableRowsElements.size()+
                        " mismatch with given rows : "+noOfRows, (tableRowsElements.size() != Integer.parseInt(noOfRows)));
            }else{
                Assert.assertFalse("no of rows is not defined", true);
            }
        } catch (Exception ex) {
            Assert.assertFalse("verifyThatRowsAreDisplayed with rows : "+noOfRows+" display failed due to : "
                    + ex.getMessage(), true);
        }
    }


    public void clickOnButton(String filterButton) throws Exception{
        try {
            if(!StringUtils.isBlank(filterButton)){
                clickElementUsingJQuery(filtersButtonElement);
            }else{
                Assert.assertFalse("filter button is not defined", true);
            }
        } catch (Exception ex) {
            Assert.assertFalse("clickOnButton with : "+filterButton+" failed due to : "
                    + ex.getMessage(), true);
        }
    }


    public void filterRecordsByValueAndValue(String marketCap, String marketCapValues, String price,
                                             String priceValues) throws Exception{
        try {
            if(!StringUtils.isBlank(marketCap) && !StringUtils.isBlank(marketCapValues)
                    && !StringUtils.isBlank(price) && !StringUtils.isBlank(marketCap)){
                FrontEndTasksContext.marketCap = marketCap;
                FrontEndTasksContext.marketCapValues = marketCapValues;
                FrontEndTasksContext.price = price;
                FrontEndTasksContext.priceValues = priceValues;
                waitUntilVisible(filtersIconElement);
                for(WebElement filterIconElement : filterIconElements){
                    moveFocusOnElement(filterIconElement);
                    WebElement filterIconButtonElement = getElement(filterIconElement, "button_tagname");
                    if(getText(filterIconButtonElement).contains("Add Filter")){
                        clickElement(filterIconButtonElement);
                        break;
                    }
                }
                waitUntilVisible(filterPopupElement);
                List<WebElement> buttonElements = getElements(filterPopupElement, "filter_popup_element");
                boolean marketCapClicked = false;
                boolean priceCapClicked = false;
                for(WebElement buttonElement : buttonElements){
                    moveFocusOnElement(buttonElement);
                    if(getText(buttonElement).contains(marketCap)){
                        clickElement(buttonElement);
                        WebElement marketCapRangeDiv = getElement("market_cap_range_div");
                        List<WebElement> marketCapRangeInputElements = getElements(marketCapRangeDiv, "input_tagname");
                        String[] capRange = marketCapValues.split("-");
                        if(capRange == null || capRange.length != 2){
                            Assert.assertFalse("Market Cap Range values are inappropriate", true);
                        }else{
                            enterText(marketCapRangeInputElements.get(0), capRange[0].trim());
                            enterText(marketCapRangeInputElements.get(1), capRange[1].trim());
                        }
                        clickElement(applyFilterButton);
                        marketCapClicked = true;
                    }else if(getText(buttonElement).contains(price)){
                        clickElement(buttonElement);
                        WebElement priceRangeDiv = getElement("price_range_cap_div");
                        List<WebElement> priceRangeInputElements = getElements(priceRangeDiv, "input_tagname");
                        String[] priceRange = priceValues.split("-");
                        if(priceRange == null || priceRange.length != 2){
                            Assert.assertFalse("Price Range values are inappropriate", true);
                        }else{
                            enterText(priceRangeInputElements.get(0), priceRange[0].trim());
                            enterText(priceRangeInputElements.get(1), priceRange[1].trim());
                        }
                        clickElement(applyFilterButton);
                        priceCapClicked = true;
                    }
                    if(marketCapClicked && priceCapClicked){
                        break;
                    }
                }
                clickElement(showResultsButton);
            }else{
                Assert.assertFalse("filter record values are not defined", true);
            }
        } catch (Exception ex) {
            Assert.assertFalse("filterRecordsByValueAndValue failed due to : "
                    + ex.getMessage(), true);
        }
    }


    /**
     * once the filter is applied get the rows displayed on UI and compare every row price and market cap value
     * falls in the range we filtered
     *
     * @return
     */
    public void verifyRecordsDisplayedOnPageAreCorrectAsPerTheFilterApplied() throws Exception{
        try {
            scrollIntoView(500);
            WebElement tableBodyElement = getElement("currency_details");
            waitUntilElementRefreshed(tableBodyElement);
            List<WebElement> tableRowsElements = getElements(tableBodyElement, "tr_tagname");
            for(WebElement tr : tableRowsElements){
                moveFocusOnElement(tr);
                List<WebElement> tableTdElements = getElements(tr, "td_tagname");
                String rowNum = getText(getElement(tableTdElements.get(1), "p_tagname"));
                if(FrontEndTasksContext.price.equalsIgnoreCase("Price")){
                    WebElement priceValueElement = getWebElement(tableTdElements.get(3), "div > a", "css", false);
                    moveFocusOnElement(priceValueElement);
                    String priceValue = getText(priceValueElement);
                    priceValue = priceValue.replace("$", "").replace(",", "").trim();
                    if(!StringUtils.isBlank(priceValue)){
                        String[] priceRange = FrontEndTasksContext.priceValues.split("-");
                        if(priceRange == null || priceRange.length != 2){
                            Assert.assertFalse("Price Range values are inappropriate", true);
                        }else{
                            Double priceValueUI = Double.parseDouble(priceValue);
                            if(priceValueUI < Double.parseDouble(priceRange[0]) || priceValueUI > Double.parseDouble(priceRange[1])){
                                Assert.assertFalse("for the row number : "+rowNum+" price value UI : "+priceValueUI+" doesnt fall under the given range "+FrontEndTasksContext.priceValues, true);
                            }
                        }
                    }
                }
                if(FrontEndTasksContext.marketCap.equalsIgnoreCase("Market Cap")){
                    WebElement marketCapElement = getWebElement(tableTdElements.get(6), "p > span[class='sc-1ow4cwt-1 ieFnWP']", "css", false);
                    moveFocusOnElement(marketCapElement);
                    String marketCapValue = getText(marketCapElement);
                    marketCapValue = marketCapValue.replace("$", "").replace(",", "").trim();
                    if(!StringUtils.isBlank(marketCapValue)){
                        String[] marketCapRange = FrontEndTasksContext.marketCapValues.split("-");
                        if(marketCapRange == null || marketCapRange.length != 2){
                            Assert.assertFalse("Market Cap Range values are inappropriate", true);
                        }else{
                            long marketCapValueUI = Long.parseLong(marketCapValue);
                            if(marketCapValueUI < Long.parseLong(marketCapRange[0]) || marketCapValueUI > Long.parseLong(marketCapRange[1])){
                                Assert.assertFalse("for the row number : "+rowNum+" market cap value UI : "+marketCapValueUI+" doesnt fall under the given range "+FrontEndTasksContext.marketCapValues, true);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Assert.assertFalse("verifyRecordsDisplayedOnPageAreCorrectAsPerTheFilterApplied failed due to : "
                    + ex.getMessage(), true);
        }
    }


}
