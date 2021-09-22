package com.cmcp.test.steps.frontEnd;

import com.cmcp.test.pages.PageObjectFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * Created by Roshan
 *
 * This is a Front End Feature file step definitions class
 * where cucumber BDD format step definitions are implemented
 * along with Page Object Model design pattern
 */
public class FrontEndStepsImpl {


    @Given("user should launch the coin market cap website")
    public void userShouldLaunchTheCoinMarketCapWebsite() throws Exception{
        PageObjectFactory.getCoinMarketCapHomePage().userShouldLaunchTheCoinMarketCapWebsite();
    }

    @When("click on {string} drop down and select {string} rows")
    public void clickOnDropDownAndSelectRows(String dropDownType, String noOfRows) throws Exception{
        PageObjectFactory.getCoinMarketCapHomePage().clickOnDropDownAndSelectRows(dropDownType, noOfRows);
    }


    @Then("verify that {string} rows are displayed")
    public void verifyThatRowsAreDisplayed(String noOfRows) throws Exception{
        PageObjectFactory.getCoinMarketCapHomePage().verifyThatRowsAreDisplayed(noOfRows);
    }


    @When("click on {string} button")
    public void clickOnButton(String filterButton) throws Exception{
        PageObjectFactory.getCoinMarketCapHomePage().clickOnButton(filterButton);
    }

    @And("Filter records by {string} value {string} and {string} value {string}")
    public void filterRecordsByValueAndValue(String marketCap, String marketCapValues, String price,
                                             String priceValues) throws Exception{
        PageObjectFactory.getCoinMarketCapHomePage().filterRecordsByValueAndValue(marketCap, marketCapValues, price, priceValues);
    }

    @Then("verify records displayed on page are correct as per the filter applied")
    public void verifyRecordsDisplayedOnPageAreCorrectAsPerTheFilterApplied() throws Exception{
        PageObjectFactory.getCoinMarketCapHomePage().verifyRecordsDisplayedOnPageAreCorrectAsPerTheFilterApplied();
    }
}
