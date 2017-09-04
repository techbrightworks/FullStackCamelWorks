/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.fecamelworks.webconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * The Class FECamelWorksViewConfiguration.
 */
@Configuration
public class FECamelWorksViewConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(FECamelWorksViewConfiguration.class);

	/**
	 * FeviewResolver.
	 *
	 * @return the view resolver
	 */
	@Bean
	public ViewResolver feviewResolver() {
		logger.info("ViewConfiguration feviewResolver()");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setPrefix("/WEB-INF/displays/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}
