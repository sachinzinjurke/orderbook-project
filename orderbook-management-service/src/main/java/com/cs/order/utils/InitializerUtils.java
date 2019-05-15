package com.cs.order.utils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import com.cs.constants.OrderType;
import com.cs.core.LimitOrder;
import com.cs.core.MarketOrder;
import com.cs.core.Order;

public class InitializerUtils {
	
	public static Order createRandomOrder() {
		Order order=null;
		OrderType randomOrderType=OrderType.getRandomOrderType();
		if(randomOrderType==OrderType.LIMIT) {
			order=new LimitOrder(getRandomQuantityInRange(), getRandomQuantityInRange(), new Date(), getRandomInstrumentIdInRange(), getRandomPriceInRange());
		}else {
			order=new MarketOrder(getRandomQuantityInRange(), getRandomQuantityInRange(), new Date(), getRandomInstrumentIdInRange(), getRandomPriceInRange());
		}
		return order;
	}

	public static Double getRandomPriceInRange() {
		DecimalFormat df = new DecimalFormat("#.##");  
		double leftLimit = 1D;
	    double rightLimit = 100D;
	    double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	    return Double.valueOf(df.format(generatedDouble));
		
	}
	
	public static Integer getRandomQuantityInRange() {
		int leftLimit = 1;
	    int rightLimit = 10;
	    int generatedInteger = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
		return generatedInteger;
	}
	public static Integer getRandomInstrumentIdInRange() {
		int leftLimit = 1;
	    int rightLimit = 4;
	    int generatedInteger = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
		return generatedInteger;
	}
	
}
