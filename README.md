# FxApiBddTest

The BDD tests are created in Cucumber+REST Assured framework with some Lambda expressions. 

In order to test the latest FX rates API response, "ApiToGetLatestData.feature" file is used.

In order to test the past FX rates API response, "ApiToGetPastConversionRates.feature" file is used

Both feature files contains position and negative test cases. During execution, the positive test cases will shown as passed. While the negative test cases status will be shown as skipped.

These test cases are very much parameterized in feature file. In order to add more test cases, tester needs to add test data row and rest will be picked by framework. 

The result report can be seen on "index.html" file in "build/CucumberReports" folder.
  
Manual test cases are kept in resources folder.