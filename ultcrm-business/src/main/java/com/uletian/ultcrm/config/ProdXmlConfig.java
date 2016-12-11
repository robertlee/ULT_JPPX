package com.uletian.ultcrm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource({"classpath:applicationall.xml","classpath:applicationprod.xml"})
@Profile("prod")
public class ProdXmlConfig {

}
