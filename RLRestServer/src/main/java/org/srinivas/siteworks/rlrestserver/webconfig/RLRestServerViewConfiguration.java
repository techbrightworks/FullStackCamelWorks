/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.rlrestserver.webconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

/**
 * The Class RLRestServerViewConfiguration.
 */
public class RLRestServerViewConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(RLRestServerViewConfiguration.class);

	/**
	 * RLviewResolver.
	 *
	 * @return the view resolver
	 */
	@Bean
	public ViewResolver rlviewResolver() {
		logger.info("ViewConfiguration rlviewResolver()");
		return new ContentNegotiatingViewResolver();
	}

}
