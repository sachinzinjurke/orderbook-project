package com.cs.order.config;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.cs.core.Order;
import com.cs.core.OrderBook;
import com.cs.order.constants.Cache;
import com.cs.order.utils.InitializerUtils;

@Component
public class OrderBookInitializer implements ApplicationListener<ContextRefreshedEvent>{

	private static final Logger logger = LoggerFactory.getLogger(OrderBookInitializer.class.getName());
	
	@Resource(name="instrumentMap")
	Map<Integer,OrderBook>instrumentMap;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		logger.info("############Application context initialized############");	
		logger.info("###########Pre-Loading Instruments with orders#########");
		OrderBook book=Cache.INSTRUMENT_CACHE.get(1);
		logger.info("$$$$$$$$$$book " + book);
		
	//	instrumentMap.entrySet().stream().forEach(entry->System.out.println(entry));
		/*
		 * String[] beans = event.getApplicationContext().getBeanDefinitionNames();
		 * Arrays.sort(beans); for (String bean : beans) { logger.info(bean); }
		 */
		
		Supplier<Order> orderSupplier= ()->{
			return InitializerUtils.createRandomOrder();
		};
		
		Stream.generate(orderSupplier).peek((orderInstruId)->{
			logger.info("Processing order :: "+ orderInstruId);
		}).limit(10)
		.forEach((order)->{
			logger.info("###########Cached Book#########",Cache.INSTRUMENT_CACHE.get(order.getInstrumentId()).getOrders().add(order));
			//logger.info("###########Cached Book#########",instrumentMap.get(order.getInstrumentId()).getOrders().add(order));
				});
		logger.info("###########Pre-Loading Instruments done#########");
		logger.info("###########Orders Loaded Into OrderBook#########");
		Cache.INSTRUMENT_CACHE.entrySet().stream().forEach(entry->System.out.println(entry));
		//instrumentMap.entrySet().stream().forEach(entry->System.out.println(entry));
		
		logger.info("sorting");
		List<Order> collect = Cache.INSTRUMENT_CACHE.get(1).getOrders().stream().sorted(Comparator.comparing(Order::getQuantity)).collect(Collectors.toList());
		//logger.info("sorted list " ,collect.get(0));
		if(!collect.isEmpty()) {
			logger.info("not empry " + collect);
		}
	}

	private Order createRandomOrder() {
		return null;
	}
}
