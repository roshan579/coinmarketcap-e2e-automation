Feature: Front End Tasks for CoinMarketCap Website

  User Should be able to verify the front end tasks to display rows and filter records for the crypto currencies displayed

  @FrontEndTask_TC01_Test
  Scenario Outline: verify the no of rows displayed on coin market cap website
    Given user should launch the coin market cap website
    When click on "Show Rows" drop down and select "<No Of Rows>" rows
    Then verify that "<No Of Rows>" rows are displayed

    Examples:

    | No Of Rows |
    | 100        |
    | 50         |
    | 20         |


  @FrontEndTask_TC02_Test
  Scenario: verify the filter functionality based on the given filter criteria
    Given user should launch the coin market cap website
    When click on "Filters" button
    And Filter records by "Market Cap" value "1000000000-10000000000" and "Price" value "101-1000"
    Then verify records displayed on page are correct as per the filter applied


















