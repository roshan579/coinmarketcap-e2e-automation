Feature: BackEnd Tasks for CoinMarketCap Website

  User Should be able to retrieve APIs from CoinMarketCap Website and verify backend tasks

  @BackEndTask_TC01_Test @NoBrowser
  Scenario: get the crypto currencies id and convert desired amount to Bolivian
    Given setup the base uri and path for testing
    When do GET call to "CRYPTO_CURRENCY_MAP_SERVICE_INFO" service
    Then convert desired amount for every id into Bolivian


  @BackEndTask_TC02_Test @NoBrowser
  Scenario: retrieve ETH id 1027 and validate the necessary fields
    Given setup the base uri and path for testing
    When do GET call to "CRYPTO_CURRENCY_INFO_SERVICE_INFO" service with 1027 id
    Then Confirm that the following logo URL is present "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"
    And  Confirm that the technical_doc Url is present  "https://github.com/ethereum/wiki/wiki/White-Paper"
    And  Confirm that the symbol of the currency is "ETH"
    And  Confirm that the date added is  "2015-08-07T00:00:00.000Z"
    And  Confirm that the currency has the mineable tag associated with it "mineable"


  @BackEndTask_TC03_Test @NoBrowser
  Scenario: retrieve ETH id 1027 and validate the necessary fields
    Given setup the base uri and path for testing
    When do GET call to "CRYPTO_CURRENCY_INFO_SERVICE_INFO" service with "1,2,3,4,5,6,7,8,9,10" ids
    Then Check which currencies have the "mineable" tag associated with them
    And Verify that the correct cryptocurrencies have been printed out












