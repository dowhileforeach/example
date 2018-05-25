package ru.dwfe.example.html.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class CountryCode
{
  public static void main(String[] args) throws IOException
  {
    final AtomicInteger maxLength_country = new AtomicInteger(0);
    final AtomicInteger maxLength_countryPhoneCode = new AtomicInteger(0);

    Document doc = Jsoup.connect("https://countrycode.org/").get();
    Elements countries = doc
            .select("table").get(0) // because there are 2 tables
            .select("tbody > tr");

    countries.forEach(next -> {
      Elements tdList = next.select("td");
      String country = tdList.get(0).select("a").text().trim();    // Russia
      String countryPhoneCode = tdList.get(1).text().trim();       // 7
      String[] countryCodeStr = tdList.get(2).text().split(" / ");
      String countryCodeStrShort = countryCodeStr[0].trim();       // RU
      String countryCodeStrLong = countryCodeStr[1].trim();        // RUS

      if (country.length() > maxLength_country.get())
        maxLength_country.set(country.length());
      if (countryPhoneCode.length() > maxLength_countryPhoneCode.get())
        maxLength_countryPhoneCode.set(countryPhoneCode.length());

      System.out.println(String.format("%-40s %s %s %s",
              country, countryCodeStrShort, countryCodeStrLong, countryPhoneCode));
    });
    System.out.println("-----------------------------------------------------");
    System.out.println(String.format("number of countries              = %s", countries.size()));
    System.out.println(String.format("max length of country name       = %s", maxLength_country.get()));
    System.out.println(String.format("max length of country phone code = %s", maxLength_countryPhoneCode.get()));
  }
}
