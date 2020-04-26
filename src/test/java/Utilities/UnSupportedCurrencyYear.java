package Utilities;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnSupportedCurrencyYear {

    List <String> listCurrency = new ArrayList<String>(Arrays.asList("MXN",
            "ZAR",
            "INR",
            "THB",
            "CNY",
            "AUD",
            "ILS",
            "KRW",
            "JPY",
            "PLN",
            "GBP",
            "IDR",
            "HUF",
            "PHP",
            "TRY",
            "RUB",
            "HKD",
            "ISK",
            "DKK",
            "MYR",
            "CAD",
            "USD",
            "BGN",
            "NOK",
            "RON",
            "SGD",
            "CZK",
            "SEK",
            "NZD",
            "BRL",
            "EUR"));
    List<String> dateList = new ArrayList<>(Arrays.asList(
            "1999-08-10",
            "2000-10-10",
            "2001-09-10",
            "2002-10-10",
            "2003-10-12",
            "2004-11-10",
            "2005-12-08",
            "2006-10-01",
            "2007-10-10",
            "2008-10-10",
            "2009-01-10",
            "2010-09-02",
            "2011-08-02",
            "2012-03-02",
            "2013-08-12",
            "2014-02-01",
            "2015-01-10",
            "2016-09-02",
            "2017-07-06",
            "2018-08-07",
            "2019-10-01",
            "2020-03-01"));
    @Test
    public void currencyTest(){
        for (String date:dateList){
            for (String list:listCurrency){
            RestAssured.baseURI = "https://api.ratesapi.io/api";
            RequestSpecification httpsRequest = RestAssured.given();
            Response response = httpsRequest.request(Method.GET, "/"+date+"?base="+list);
                JSONObject responseJsonObject = new JSONObject(response.getBody().asString());
                if (responseJsonObject.has("error")){
                    System.out.println("The Unsupported base currency: "+list+ " for Year: "+date.substring(0, 4));
                }
            }
        }
    }
}

