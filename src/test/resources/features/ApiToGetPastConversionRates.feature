Feature: Foreign currency exchange rates api with currency conversion

  @Date
  Scenario Outline: API to get the past conversion rate based on base and symbols currencies
    Given rates API for Latest Foreign Exchange rates with date, baseCurrency and symbolCurrencies "<date>","<baseCurrency>","<symbolCurrency>","<TestCaseDescription>"
    When a request is made to fetch the rates
    Then verify that the valid response is received
    And verify that the response has base currency, FX rate of all the other currencies and date is current date or last working date
    Examples:
      |TestCaseDescription                                                                                                                        |baseCurrency | symbolCurrency                |date        |
      | Positive - Empty(Default) base currency, default symbol currency and valid date in between today/last working day and 1999                |             |                               | 2019-04-12 |
      | Positive - Empty(Default) base currency, default symbol currency and valid future date                                                    |             |                               | 2020-05-09 |
      | Positive - Non-default  base currency, default symbol currency  and valid date in between today/last working day and 1999                 | USD         |                               | 2019-04-11 |
      | Positive - Non-default  base currency, default symbol currency  and valid future date                                                     | ZAR         |                               | 2021-01-09 |
      | Positive - Default base currency, one symbol currency and valid date in between today/last working day and 1999                           |             | GBP                           | 1999-04-12 |
      | Positive - Default base currency, one symbol currency and valid future date                                                               |             | INR                           | 2020-07-01 |
      | Positive - Default base currency, 2 non-EUR symbol currency and valid date in between today/last working day and 1999                     |             | GBP,CHF                       | 2019-04-12 |
      | Positive - Default base currency, 2 non-EUR symbol currency and valid future date                                                         |             | PLN,CNY                       | 2020-09-01 |
      | Positive - Non-default in base currency & 20 symbol currency and valid date in between today/last working day and 1999                    | GBP         | PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL | 2018-04-12 |
      | Positive - Default in base currency & 20 symbol currency and valid future date                                                            |             | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR | 2020-05-01 |
      | Positive Edge Case - Default base currency & 30 symbol currency excluding EUR with valid date in between today/last working day and 1999  |             | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL | 2020-04-12 |
      | Positive Edge Case - Default base currency & 30 symbol currency excluding EUR and valid future date                                       |             | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL | 2020-05-09 |
      | Positive Edge Case - Non-Default base currency, 31 symbol currency including EUR and valid date in between today/last working day and 1999| CHF         | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL,EUR | 2018-04-12 |
      | Positive Edge Case - Non-Default base currency, 31 symbol currency including EUR and valid future date                                    | CAD         | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL,EUR | 2020-06-09 |
      | Negative - Empty(Default) base currency and default symbol currency and valid date with year 1998                                         |             |                               | 1998-04-12 |
      | Negative - Non-default  base currency, default symbol currency  and valid date with year 1997                                             |             |                               | 1997-04-12 |
      | Negative - Default base currency, one symbol currency  valid date with year 1998                                                          |             |                               | 1998-05-13 |
      | Negative - Default base currency, 2 non-EUR symbol currency and valid date with year 1998                                                 |             |                               | 1998-05-13 |
      | Negative - Default base currency, 30 symbol currency excluding EUR and valid date with year 1998                                          |             | MXN,ZAR,INR,THB,CNY,AUD,ILS,KRW,JPY,RUB,PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL | 1998-05-13 |
      | Negative - Non-default in base currency, 20 symbol currency and valid date with year 1998                                                 | AUD         | PLN,GBP,IDR,HUF,PHP,TRY,HKD,ISK,DKK,MYR,CAD,USD,BGN,NOK,RON,SGD,CZK,SEK,NZD,BRL | 1998-05-13 |
      | Negative - default base currency and an Invalid symbol-currency and valid date in between today/last working day and 1999                 |             | XXX                           | 2002-04-12 |
      | Negative - Invalid base currency and empty symbol-currency and valid date in between today/last working day and 1999                      | ABC         |                               | 2002-04-12 |
      | Negative - valid non-EUR base currency and one invalid symbol-currency  and valid date in between today/last working day and 1999         | ABC         | XXX                           | 2002-04-12 |
      | Negative - Default base currency, one symbol currency and Invalid date                                                                    |             |                               | 2002-04-31 |