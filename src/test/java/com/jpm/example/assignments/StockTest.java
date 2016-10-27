package com.jpm.example.assignments;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.jpm.sample.assignments.Stock;
import com.jpm.sample.assignments.Trade;
import com.jpm.sample.assignments.enums.StockType;
import com.jpm.sample.assignments.enums.TradeType;

public class StockTest {

	@Test
	public void testDividend() {
        Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
        Stock stockGIN = new Stock("GIN", StockType.PREFERRED, 8.0, 0.2, 100.0);
        // Test dividend for Common
		Double dividendALE = stockALE.dividend(1.0);
		Double expectedDividendALE = stockALE.getLastDividend()/1.0;
		assertEquals(expectedDividendALE, dividendALE, 0.0);
		// Test dividend for Preferred
		Double dividendGIN = stockGIN.dividend(1.0);
		Double expectedDividendGIN = stockGIN.getFixedDividend()*stockGIN.getParValue()/1.0;
		assertEquals(expectedDividendGIN, dividendGIN, 0.0);
	}

	@Test
	public void testPERatio() {
        Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
        Double peRatioALE = stockALE.PERatio(1.0);
        Double expectedPeRatioALE = 1.0/stockALE.getLastDividend();
        assertEquals(expectedPeRatioALE, peRatioALE, 0.0);
	}

	@Test
	public void testBuy() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		stockALE.buy(1, 10.0);
		assertEquals(10.0, stockALE.getPrice(), 0.0);
	}

	@Test
	public void testSell() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		stockALE.sell(1, 10.0);
		assertEquals(10.0, stockALE.getPrice(), 0.0);
	}

	@Test
	public void testGetPrice() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		stockALE.sell(1, 10.0);
		stockALE.buy(1, 11.0);
		assertEquals(11.0, stockALE.getPrice(), 0.0);
	}

	@Test
	public void testCalculateVolumeWeightedStockPrice() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		stockALE.sell(2, 10.0);
		stockALE.buy(2, 10.0);		
		Double volumeWeightedStockPrice = stockALE.calculateVolumeWeightedStockPrice();
		assertEquals(10.0, volumeWeightedStockPrice, 0.0);
		Date now = new Date();
		// Date 20 minutes ago
		Date startTime = new Date(now.getTime() - (20 * 60 * 1000));
		stockALE.getTrades().put(startTime, new Trade(TradeType.BUY, 1, 20.0));
		// The new (outdated) trade should not affect calculation of the Volume Weighted Stock Price
		volumeWeightedStockPrice = stockALE.calculateVolumeWeightedStockPrice();
		assertEquals(10.0, volumeWeightedStockPrice, 0.0);
	}
}
