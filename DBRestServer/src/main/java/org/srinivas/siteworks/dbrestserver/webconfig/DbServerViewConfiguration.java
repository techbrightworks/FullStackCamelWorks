/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.dbrestserver.webconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

/**
 * The Class DbServerViewConfiguration.
 */
public class DbServerViewConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(DbServerViewConfiguration.class);

	/**
	 * Dbviewresolver.
	 *
	 * @return the viewresolver
	 */
	@Bean
	public ViewResolver dbviewResolver() {
		logger.info("ViewConfiguration dbviewResolver()");
		return new ContentNegotiatingViewResolver();
	}

}
