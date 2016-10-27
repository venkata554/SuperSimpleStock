package com.jpm.sample.assignments;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jpm.sample.assignments.enums.StockType;

@Configuration
@ComponentScan
public class StockApp 
{
	 
	
    @Bean
    Map<String, Stock> Db() {
        HashMap<String, Stock> db = new HashMap<String, Stock>();
        db.put("TEA", new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0));
        db.put("POP", new Stock("POP", StockType.COMMON, 8.0, 0.0, 100.0));
        db.put("ALE", new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0));
        db.put("GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 0.2, 100.0));
        db.put("JOE", new Stock("JOE", StockType.COMMON, 13.0, 0.0, 250.0));
        return db;
    }

    public static void main( String[] args )
    {
        try {
            ApplicationContext context = 
                    new AnnotationConfigApplicationContext(StockApp.class);
            
            // Run dividend and P/E Ratio routines
            @SuppressWarnings("unchecked")
    		Map<String, Stock> db = context.getBean("Db", Map.class);
            for (Stock stock: db.values()) {
            	System.out.println( stock.getSymbol() + " dividend: " + stock.dividend(9.1));
            	System.out.println( stock.getSymbol() + " P/E Ratio: " + stock.PERatio(9.1));
                // Record some trades
            	for (int i=1; i <= 10; i++) {
            		Random r = new Random();
            		Integer rangeMin = 1;
            		Integer rangeMax = 10;
            		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            		stock.buy(i, randomValue);
            		System.out.println( stock.getSymbol() + " bought " + i + " shares at $" + randomValue);
            		randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            		stock.sell(i, randomValue);
            		System.out.println( stock.getSymbol() + " sold " + i + " shares at $" + randomValue);
            		 
            	}
            	System.out.println( stock.getSymbol() + " price: $" + stock.getPrice());
            	System.out.println( stock.getSymbol() + " volumeWeightedStockPrice: $" + stock.calculateVolumeWeightedStockPrice());
            }
            Double GBCEallShareIndex = GBCE.allShareIndex(db);
            System.out.println( "GBCE All Share Index: " + GBCEallShareIndex);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
