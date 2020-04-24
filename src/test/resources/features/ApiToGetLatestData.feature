Feature: Foreign currency exchange rates api with currency conversion

  @latest
  Scenario Outline: API to get the latest the FX data based on base and symbols currencies
    Given rates API for Latest Foreign Exchange rates with base and symbols "<baseCurrency>","<symbolCurrencies>","<TestCaseDescription>"
    When a request is made to fetch the rates
    Then verify that the valid response is received
    And verify that the response has base currency, FX rate of all the other currencies and date is current date or last working date
    Examples:
      |TestCaseDescription                                                                                            |baseCurrency     |symbolCurrencies               |
      | Positive - Empty(Default) base currency and default symbol currency                                           |                 |                               |
      | Positive - Non-default  base currency and default symbol currency                                             | USD             |                               |
      | Positive - Default base currency & one symbol currency                                                        |                 | USD                           |
      | Positive - Default base currency & 2 non-EUR symbol currency                                                  |                 | GBP,CHF                       |
      | Positive - Input EUR (Default though) in base currency & 2 non-EUR symbol currency                            | EUR             | ZAR,INR                       |
      | Positive - Non-default in base currency & 20 symbol currency                                                  | RUB             | PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL |
      | Positive - Default in base currency & 20 symbol currency                                                      |                 | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR |
      | Positive Edge Case - Default base currency & 30 symbol currency excluding EUR                                 |                 | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL |
      | Positive Edge Case - Non-Default base currency & 31 symbol currency including EUR                             | MXN             | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL,EUR |
      | Positive- Non-EUR Base currency & 2 non-EUR symbol currency where one of the symbol-currency is base-currency | THB             | THB,AUD                       |
      | Positive - Non-Default base currency & 2 symbol currency where both are same (duplicate symbol currency)      | USD             | GBP,GBP                       |
      | Negative - default base currency and an Invalid symbol-currency                                               |                 | XXX                           |
      | Negative - Invalid base currency and empty symbol-currency                                                    | ABC             |                               |
      | Negative - valid non-EUR base currency and one invalid symbol-currency                                        | ABC             | XXX                           |
      | Negative - valid non-EUR base currency and two symbol-currency where the first is invalid                     | PLN             | YYY,IDR                       |
      | Negative - valid non-EUR base currency and two symbol-currency where the second is invalid                    | HUF             | TRY,ZZZ                       |
      | Negative - Non-Default base currency & 5 symbol currency where one of the currency is valid but doesn't exist in rate API currency list i.e. BDT | INR | KRW,JPY,PLN,ISK,BDT |
      | Negative Edge Case - Non-Default base currency & 32 symbol currencies where 32nd symbol currency is invalid   | ZAR             | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL,EUR,XXX |
      | Negative Edge Case - Incorrect URL                                                                            | ABCDEFG         | ABCXYZPQ                      |