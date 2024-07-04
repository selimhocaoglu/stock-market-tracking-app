package com.stocktracker;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockApiClient {
    private static final String API_KEY = "NULL"; //API_KEY için ayarlamalar sonradan yapılacaktır. Şimdilik local'den hallet.
    private static final String API_URL_FIRST_PART = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=";
    private static final String API_URL_LAST_PART = "&interval=1min&apikey=";

    public static double getStockPrice(String symbol) throws Exception {
        String urlString = API_URL_FIRST_PART + symbol + API_URL_LAST_PART + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (Reader reader = new InputStreamReader(conn.getInputStream())) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject timeSeries = jsonObject.getAsJsonObject("Time Series (1min)");
            String latestTime = timeSeries.keySet().iterator().next();
            JsonObject latestData = timeSeries.getAsJsonObject(latestTime);
            return latestData.get("1. open").getAsDouble();
        }
    }
}
