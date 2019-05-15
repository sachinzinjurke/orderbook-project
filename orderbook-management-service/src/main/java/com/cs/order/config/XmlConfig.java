package com.cs.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:orderbook-context.xml"})
public class XmlConfig {

}
