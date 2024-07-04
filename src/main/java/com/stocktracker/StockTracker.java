package com.stocktracker;

import java.util.Timer;
import java.util.TimerTask;

public class StockTracker {
    private String stockSymbol;
    private double targetPrice;
    private double currentPrice;

    public StockTracker(String stockSymbol, double targetPrice) {
        this.stockSymbol = stockSymbol;
        this.targetPrice = targetPrice;
    }
    public StockTracker(String stockSymbol){
        this.stockSymbol = stockSymbol;
        this.targetPrice = 0.0;
    }

    public void startTracking() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    currentPrice = StockApiClient.getStockPrice(stockSymbol);
                    if (currentPrice >= targetPrice) {
                        sendNotification();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60000);
    }

    private void sendNotification() {
        if(targetPrice > 0) {
            System.out.println("Hisse senedi " + stockSymbol + " belirlenen fiyat seviyesine ulaştı: " + targetPrice);
        }
        System.out.println("Current price: " + currentPrice);

    }


}

