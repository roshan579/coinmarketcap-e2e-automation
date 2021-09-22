package com.cmcp.test.steps.backEnd;

import com.cmcp.test.pages.PageObjectFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * Created by Roshan
 *
 * This is a BAck End Feature file step definitions class
 * where cucumber BDD format step definitions are implemented
 * along with Page Object Model design pattern
 */
public class BackEndStepsImpl {


    @Given("setup the base uri and path for testing")
    public void setupTheBaseUriAndPathForTesting() throws Exception{
        PageObjectFactory.getBackEndAPIPage().setupTheBaseUriAndPathForTesting();
    }

    @When("do GET call to {string} service")
    public void doGETCallToService(String serviceInfo) throws Exception{
        PageObjectFactory.getBackEndAPIPage().doGETCallToService(serviceInfo);
    }


    @Then("convert desired amount for every id into Bolivian")
    public void convertDesiredAmountForEveryIdIntoBolivian() throws Exception{
        PageObjectFactory.getBackEndAPIPage().convertDesiredAmountForEveryIdIntoBolivian();
    }

    @When("do GET call to {string} service with {int} id")
    public void doGETCallToServiceWithId(String serviceInfo, int id) throws Exception{
        PageObjectFactory.getBackEndAPIPage().doGETCallToServiceWithId(serviceInfo, id);
    }

    @Then("Confirm that the following logo URL is present {string}")
    public void confirmThatTheFollowingLogoURLIsPresent(String logo) throws Exception{
        PageObjectFactory.getBackEndAPIPage().confirmThatTheFollowingLogoURLIsPresent(logo);
    }

    @And("Confirm that the technical_doc Url is present  {string}")
    public void confirmThatTheTechnical_docUrlIsPresent(String technicalDoc) throws Exception{
        PageObjectFactory.getBackEndAPIPage().confirmThatTheTechnical_docUrlIsPresent(technicalDoc);
    }

    @And("Confirm that the symbol of the currency is {string}")
    public void confirmThatTheSymbolOfTheCurrencyIs(String symbol) throws Exception{
        PageObjectFactory.getBackEndAPIPage().confirmThatTheSymbolOfTheCurrencyIs(symbol);
    }

    @And("Confirm that the date added is  {string}")
    public void confirmThatTheDateAddedIs(String dateAdded) throws Exception{
        PageObjectFactory.getBackEndAPIPage().confirmThatTheDateAddedIs(dateAdded);
    }

    @And("Confirm that the currency has the mineable tag associated with it {string}")
    public void confirmThatTheCurrencyHasTheMineableTagAssociatedWithIt(String tag) throws Exception{
        PageObjectFactory.getBackEndAPIPage().confirmThatTheCurrencyHasTheMineableTagAssociatedWithIt(tag);
    }

    @When("do GET call to {string} service with {string} ids")
    public void doGETCallToServiceWithIds(String serviceInfo, String idsCommaSeparated) throws Exception{
        PageObjectFactory.getBackEndAPIPage().doGETCallToServiceWithIds(serviceInfo, idsCommaSeparated);
    }

    @Then("Check which currencies have the {string} tag associated with them")
    public void checkWhichCurrenciesHaveTheTagAssociatedWithThem(String tagName) throws Exception {
        PageObjectFactory.getBackEndAPIPage().checkWhichCurrenciesHaveTheTagAssociatedWithThem(tagName);
    }

    @And("Verify that the correct cryptocurrencies have been printed out")
    public void verifyThatTheCorrectCryptocurrenciesHaveBeenPrintedOut() throws Exception {
        PageObjectFactory.getBackEndAPIPage().verifyThatTheCorrectCryptocurrenciesHaveBeenPrintedOut();
    }
}
