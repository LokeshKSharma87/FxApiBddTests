# FxApiBddTest

The BDD tests are created in Cucumber+REST Assured framework with some Lambda expressions. 

In order to test the latest FX rates API response, "ApiToGetLatestData.feature" file is used.

In order to test the past FX rates API response, "ApiToGetPastConversionRates.feature" file is used

Both feature files contain positive and negative test cases. During execution, the positive test cases will shown as passed. While the negative test cases status will be shown as skipped.

These test cases are parameterized in feature file. In order to add more test cases, tester needs to add test data row and rest will be picked by framework. 

The result report can be seen on "index.html" file in "build/CucumberReports" folder. User needs to open it in browser.
  
Manual test cases are kept in resources folder.

Run below command in order to clone the repository:

git clone https://github.com/LokeshKSharma87/FxApiBddTests-test.git

In order to run the test cases, user needs to run the FxApiRunner file.

Note:
There are few existing anomolies in the API. The details are as below:
1. When date is 1999-01-01 / 1999-01-02 / 1999-01-03, API response is a failure response (status code 400). The reason is 1999-01-01 is a holiday and next 2 days are weekends. Therefore the first working day of year 1999 is 1999-01-04. Respective test has been commented on "ApiToGetPastConversionRates.feature" file
2. Some currencies can't be used as base currency for past years. I have written a Junit test to find it out. The class name is UnSupportedCurrencyYear where 'currencyTest' junit test exists and can be run.

Currency     Unsupported Years

MXN	        1999-2007

INR	        1999-2008

THB	        1999-2004

CNY	        1999-2004

ILS	        1999-2010

IDR	        1999-2004

PHP	        1999-2004

TRY	        1999-2004

RUB	        1999-2004

MYR	        1999-2004

BGN	        1999

RON	        1999-2004

BRL	        1999-2007

ISK	        2009-2017
