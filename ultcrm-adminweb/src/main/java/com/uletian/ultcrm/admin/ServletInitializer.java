/**
 * Spring processing Initializer application code.
 * 
 * @copyright Copyright (c) 2014-2016 uletian Inc.
 *  Author: Robert Lee
 *  Create: 2016-01-20
 */

package com.uletian.ultcrm.admin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TestCrmAdminWebApplication.class);
	}

}
