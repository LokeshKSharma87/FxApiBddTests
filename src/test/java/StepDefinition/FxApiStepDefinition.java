package StepDefinition;

import Utilities.Utilities;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Assume;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ServiceConfigurationError;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class FxApiStepDefinition {

    Logger logger = Logger.getLogger(FxApiStepDefinition.class.getName());
    String baseURI = "https://api.ratesapi.io/api";
    String path;
    String base;
    Response response;
    int successResponseCode = 200;
    int failResponseCode = 400;
    ArrayList<String> expectedCurrencies =new ArrayList<>(Arrays.asList("CHF","HRK","MXN","ZAR","INR","THB","CNY","AUD","ILS","KRW","JPY","PLN","GBP","IDR","HUF","PHP","TRY","RUB","HKD","ISK","DKK","MYR","CAD","USD","BGN","NOK","RON","SGD","CZK","SEK","NZD","BRL"));
    ArrayList<String> totalCurrencies = new ArrayList<>(Arrays.asList("CHF","HRK","MXN","ZAR","INR","THB","CNY","AUD","ILS","KRW","JPY","PLN","GBP","IDR","HUF","PHP","TRY","RUB","HKD","ISK","DKK","MYR","CAD","USD","BGN","NOK","RON","SGD","CZK","SEK","NZD","BRL","EUR"));
    LocalDate localDate;
    String invalidDate="";
    private Scenario scenario;
    String testCaseDescription;

//    For reporting
    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("rates API for Latest Foreign Exchange rates with base and symbols {string},{string},{string}")
    public void rates_API_for_Latest_Foreign_Exchange_rates_with_base_and_symbols(String baseCurrency, String symbols, String testCaseDescription) {
        this.testCaseDescription = testCaseDescription;
//      In default case, the expected base currency is EUR
        if (baseCurrency.equals("Default") || baseCurrency.trim().equals(""))
            base="EUR";
        else
            base = baseCurrency;

//      Generating the path
        path = Utilities.getPathLatest(baseCurrency, symbols);
//        Logger.info is to write the logs on console
        logger.info("The path is: "+path);
//        scenario.write is write the logs on CucumberReport
        scenario.write("The path is: "+path);
        localDate = LocalDate.now();

//      Generating the expected rates currency list from symbol Currencies
        if (!symbols.trim().equals("")){
            expectedCurrencies.clear();
            expectedCurrencies.addAll(Utilities.getCurrencyListFromSymbols(symbols));
        }

//      When the non-default or non-EUR base currency (without any symbol currencies) is provided, the EUR will also be expected in rates currency list.
        if (!(base.trim().equals("") || base.trim().equals("EUR")) && symbols.trim().equals(""))
            expectedCurrencies.add("EUR");
        logger.info("Latest Foreign exchange rates API static values have been set up");
        scenario.write("Latest Foreign exchange rates API static values have been set up");
    }

    @Given("rates API for Latest Foreign Exchange rates with date, baseCurrency and symbolCurrencies {string},{string},{string},{string}")
    public void rates_API_for_Latest_Foreign_Exchange_rates_with_date_baseCurrency_and_symbolCurrencies(String date, String baseCurrency, String symbols, String testCaseDescription) {

        this.testCaseDescription=testCaseDescription;
//      In default case, the expected base currency is EUR
        if (baseCurrency.equals("Default") || baseCurrency.trim().equals(""))
            base = "EUR";
        else
            base = baseCurrency;

//      If provided date is future date, API will bring the current date/last working date rates. Therefore, expected date is set-up as current date.
            if (Utilities.isValidDate(date)) {
                if (LocalDate.parse(date).isAfter(LocalDate.now())) {
                    logger.info("The input date is a future date. API will extract the current/last working date FX rates");
                    scenario.write("The input date is a future date. API will extract the current/last working date FX rates");
                    localDate = LocalDate.now();
                } else
                    localDate = LocalDate.parse(date);
            } else {
                logger.info("Provided date is invalid date");
                scenario.write("Provided date is invalid date");
                invalidDate = date;
            }
//      Generating the path
        path = Utilities.getPathDate(date,baseCurrency,symbols);
        logger.info("The path is: "+path);
        scenario.write("The path is: "+path);

//      Generating the expected rates currency list from symbol Currencies
        if (!symbols.trim().equals("")){
            expectedCurrencies.clear();
            expectedCurrencies.addAll(Utilities.getCurrencyListFromSymbols(symbols));
        }

//      When the non-default or non-EUR base currency (without any symbol currencies) is provided, the EUR will also be expected in rates currency list.
        if (!(base.trim().equals("") || base.trim().equals("EUR")) && symbols.trim().equals(""))
            expectedCurrencies.add("EUR");
        logger.info("Latest Foreign exchange rates API static values have been set up");
        scenario.write("Latest Foreign exchange rates API static values have been set up");
    }

    @When("a request is made to fetch the rates")
    public void a_request_is_made_to_fetch_the_rates() {
        response = Utilities.getRates(baseURI,path);
//      When a 1)valid currency in baseCurrency & symbolCurrency and 2) valid input date are provided, success response code 200 is expected
        if ((totalCurrencies.contains(base) && totalCurrencies.containsAll(expectedCurrencies) && invalidDate.equals("")) && Utilities.DateYearCheck(localDate.toString()))
            Assert.assertEquals(successResponseCode, response.getStatusCode());
            else
//      When invalid input date are provided, failure response code 400 is expected
                if (!invalidDate.equals("")){
                    Assert.assertEquals(failResponseCode, response.getStatusCode());
                    logger.info("The provided date is invalid. Therefore the test case: XXXXXXXXXXXXXXXX  "+testCaseDescription+"  XXXXXXXXXXXXX execution has been skipped");
                    scenario.write("The provided date is invalid. Therefore the test case: XXXXXXXXXXXXXXXX  "+testCaseDescription+"  XXXXXXXXXXXXX execution has been skipped");
                    Assume.assumeTrue( "The provided date is invalid", false);
                }
                else
//      When input date year is earlier than 1999, failure response code 400 is expected
                    if (!Utilities.DateYearCheck(localDate.toString())){
                        Assert.assertEquals(failResponseCode, response.getStatusCode());
                        logger.info("The provided date is earlier than year 1999. Therefore the test case: XXXXXXXXXXXXXXXX  "+testCaseDescription+"  XXXXXXXXXXXXX execution has been skipped");
                        scenario.write("The provided date is earlier than year 1999. Therefore the test case: XXXXXXXXXXXXXXXX  "+testCaseDescription+"  XXXXXXXXXXXXX execution has been skipped");
                        Assume.assumeTrue( "The provided date is earlier than year 1999", false);
                    }
                    else
                        {
//      When a Invalid currency in baseCurrency or/and symbolCurrency is provided, failure response code 400 is expected
                            Assert.assertEquals(failResponseCode, response.getStatusCode());
                            logger.info("The Base Currency or Symbol Currency is invalid. Therefore the test case: XXXXXXXXXXXXXXXX "+testCaseDescription+"  XXXXXXXXXXXXX execution has been skipped\"");
                            scenario.write("The Base Currency or Symbol Currency is invalid. Therefore the test case: XXXXXXXXXXXXXXXX "+testCaseDescription+"  XXXXXXXXXXXXX execution has been skipped");
                            Assume.assumeTrue( "The Base Currency or Symbol Currency  is invalid", false);
                    }
        logger.info("API is up. The response status code is: "+response.getStatusCode());
        scenario.write("API is up. The response status code is: "+response.getStatusCode());
    }

    @Then("verify that the valid response is received")
    public void verify_that_the_valid_response_is_received() {
//       Validating that response body JSON contains date, rates, base
        assertThat("In response, date field doesn't exist",(new JSONObject(response.getBody().asString()).get("date")),notNullValue());
        assertThat("In response, base field doesn't exist",(new JSONObject(response.getBody().asString()).get("base")),notNullValue());
        assertThat("In response, rate field doesn't exist",(new JSONObject(response.getBody().asString()).get("rates")),notNullValue());
        logger.info("The response body is: "+new JSONObject(response.getBody().asString()).toString(2));
        scenario.write("The response body is: "+new JSONObject(response.getBody().asString()).toString(2));
    }

    @Then("verify that the response has base currency, FX rate of all the other currencies and date is current date or last working date")
    public void verify_that_the_response_has_base_currency_FX_rate_of_all_the_other_currencies_and_date_is_current_date_or_last_working_date() {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());

//       Date validation
        assertThat("Date isn't matching",Utilities.getWorkingDay(localDate), hasItem((jsonObject.get("date")).toString()));//date value assertion

//      Base currency validation
        assertThat("Base currency isn't matching",jsonObject.get("base"), equalTo(base));

        JSONObject currencies = (JSONObject) jsonObject.get("rates");
        ArrayList<String> actualCurrencies = new ArrayList<>();
        currencies.keys().forEachRemaining(currency -> actualCurrencies.add(currency));

        expectedCurrencies.stream().distinct().collect(Collectors.toList());

//      Rate currency list validation
        assertThat("Arraylist comparision (in any order) failed",actualCurrencies , containsInAnyOrder(expectedCurrencies.stream().distinct().collect(Collectors.toList()).toArray()));
        logger.info("Date, base currency and currency list validations are successful. The test case - XXXXXXXXXXXX  "+testCaseDescription+" XXXXXXXXXXXXX has been passed");
        scenario.write("Date, base currency and currency list validations are successful. The test case - XXXXXXXXXXXX  "+testCaseDescription+" XXXXXXXXXXXXX has been passed");
    }
}
