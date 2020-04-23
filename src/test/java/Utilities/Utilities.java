package Utilities;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Utilities {

//    Method to trigger the API and return the response
    public static Response getRates(String baseUri, String getPath){
        RestAssured.baseURI =baseUri;
        RequestSpecification httpsRequest = RestAssured.given();
        Response response = httpsRequest.request(Method.GET,getPath);
        return response;
    }

//    Method to provide the current/last working day
    public static List<String> getWorkingDay(LocalDate date){
        String day= date.getDayOfWeek().name();
        List<String> workDate = new ArrayList<>();

            if (day.equals("SATURDAY")){
                workDate.add(date.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                workDate.add(date.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            else
                if (day.equals("SUNDAY")){
                    workDate.add(date.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    workDate.add(date.minusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                else
                if (day.equals("MONDAY")){
                    workDate.add(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    workDate.add(date.minusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                else {
                    System.out.println("The date is : "+date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
                    workDate.add(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    workDate.add(date.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
        return workDate;
    }

//    Method to create the List from provided symbol currencies
    public static List<String> getCurrencyListFromSymbols(String symbols){
        List<String> list = Arrays.asList(symbols.split(","));
        return list;
    }

//    Method to create the path for latest date
    public static String getPathLatest(String base, String symbols){
        String path = "/latest";
        if((base.equals("Default") || base.trim().equals("")) && symbols.trim().equals(""))
            return path;
        else if (base.equals("Default") || base.trim().equals("") && !symbols.trim().equals(""))
            return path+"?symbols="+(getCurrencyListFromSymbols(symbols).toString().replace("[","")).replace("]", "").replace(" ", "");
        else if (!(base.equals("Default") || base.trim().equals("")) && symbols.trim().equals(""))
            return path+"?base="+base;
        else if (!(base.equals("Default") || base.trim().equals("")) && !symbols.trim().equals(""))
            return path+"?base="+base+"&symbols="+(getCurrencyListFromSymbols(symbols).toString().replace("[","")).replace("]", "").replace(" ", "");
        else return null;
    }

//    Method to create the path for past date
    public static String getPathDate(String date, String base, String symbols){
        String path = "/"+date;
        if((base.equals("Default") || base.trim().equals("")) && symbols.trim().equals(""))
            return path;
        else if (base.equals("Default") || base.trim().equals("") && !symbols.trim().equals(""))
            return path+"?symbols="+(getCurrencyListFromSymbols(symbols).toString().replace("[","")).replace("]", "").replace(" ", "");
        else if (!(base.equals("Default") || base.trim().equals("")) && symbols.trim().equals(""))
            return path+"?base="+base;
        else if (!(base.equals("Default") || base.trim().equals("")) && !symbols.trim().equals(""))
            return path+"?base="+base+"&symbols="+(getCurrencyListFromSymbols(symbols).toString().replace("[","")).replace("]", "").replace(" ", "");
        else return null;
    }

//    Method to validate if a provided date is valid date
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

//    Method to check if given date year is earlier than 1999
    public static boolean DateYearCheck(String inDate) {
        int inputDateYear = Integer.parseInt(inDate.substring(0,4));
        int yearLimit=1998;
        if (inputDateYear>1998){
            return true;
        }
        else
            return false;
    }
}
