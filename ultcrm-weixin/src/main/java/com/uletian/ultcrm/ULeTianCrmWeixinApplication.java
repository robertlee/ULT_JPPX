package com.uletian.ultcrm;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.uletian.ultcrm.business.service.WeixinConfig;

import com.uletian.ultcrm.weixin.ApplicationListener;
@SpringBootApplication
@EnableConfigurationProperties({WeixinConfig.class}) 
@EnableScheduling
@EnableCaching
public class ULeTianCrmWeixinApplication {
	private static Logger logger = Logger.getLogger(ULeTianCrmWeixinApplication.class);
    public static void main(String[] args) {
		logger.info("======================================================================");
		logger.info("   @@@   @@@   DD              jDDDDDDDDG D                           |\n");                     
		logger.info("   @@@   @@@   ##        LGG.  LLiG#Eiit# i                           |\n");                     
		logger.info("   @@@   @@@   ##       LtiiL.    j#G                   jGGGGi        |\n");                       
		logger.info("   @@@   @@@   ##       K   Di    j#G     #     LG      DLiiDf        |\n");                       
		logger.info("   @@@   @@@   ##       KGLLWi    j#G     #    t#GL.    Di  ff        |\n");                        
		logger.info("   @@@   @@@   ##    #  Kf        j#G     #    L# f#:   Di  ff        |\n");                       
		logger.info("   @@@   @@@   ##    #  K:        j#G     #    L# .W:   Di  ff        |\n");                       
		logger.info("   @@@   @@@   #######  tfffj:  tfD#Kft  f#f   t# L#L   Ki  fEi       |\n");                       
		logger.info("    @@@@@@     fffff#    tff:   tffffft  fff    ff   L  Ki  ff;       |\n");                       
		logger.info("======================================================================");
		logger.info("|                        ULTCRM   WEIXIN  SYSTEM                      |\n");
		logger.info("|         ULETIAN shenzhen information company copyright 2016         |\n");
		logger.info("|                                                                     |\n");
		logger.info("|                                 Author: xubin li                    |\n");
		logger.info("|                                 Date  : 2016 02 01                  |\n");
		logger.info("|                                 WEB   : www.uletian.cn              |\n");
		logger.info("=======================================================================");

		SpringApplication app = new SpringApplication(ULeTianCrmWeixinApplication.class);
    	app.addListeners(new ApplicationListener());
    	app.addInitializers(new ApplicationListener());
    	app.run(args);
    }
    
}

 
