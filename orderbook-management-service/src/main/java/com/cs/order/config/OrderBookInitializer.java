package com.cs.order.config;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.cs.core.Instrument;
import com.cs.core.Order;
import com.cs.core.OrderBook;
import com.cs.order.callback.OrderBookProcessingThread;
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
		Supplier<Order> orderSupplier= ()->{
			return InitializerUtils.createRandomOrder();
		};
		Stream.generate(orderSupplier).peek((orderInstruId)->{
			logger.info("Processing order :: "+ orderInstruId);
		}).limit(100)
		.forEach((order)->{
			logger.info("###########Cached Book#########",Cache.INSTRUMENT_CACHE.get(order.getInstrumentId()).getOrders().add(order));
			//logger.info("###########Cached Book#########",instrumentMap.get(order.getInstrumentId()).getOrders().add(order));
				});
		
		Stream.generate(orderSupplier).peek((orderInstruId)->{
			logger.info("Processing order :: "+ orderInstruId);
		}).limit(15)
		.forEach((order)->{
			logger.info("###########Cached Book#########",Cache.INSTRUMENT_CACHE_MAP.get(order.getInstrumentId()).getOrdreBook().add(order));
			//logger.info("###########Cached Book#########",instrumentMap.get(order.getInstrumentId()).getOrders().add(order));
				});
		
		logger.info("###########Pre-Loading Instruments done#########");
		logger.info("###########Orders Loaded Into OrderBook#########");
		Cache.INSTRUMENT_CACHE.entrySet().stream().forEach(entry->System.out.println(entry));
		Cache.INSTRUMENT_CACHE_MAP.entrySet().stream().forEach(entry->System.out.println(entry));
		OrderBookProcessingThread orderBookProcessingThread=(OrderBookProcessingThread)event.getApplicationContext().getBean("orderBookProcessingThread");
		ExecutorService executorService=Executors.newSingleThreadExecutor();
		executorService.submit(orderBookProcessingThread);
		logger.info("Successfully submitted thread to service");
		
	}

	private Order createRandomOrder() {
		return null;
	}
}
