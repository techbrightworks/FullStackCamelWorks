/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.rlrestserver.webconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Class RLRestServerMvcContextConfiguration.
 */
@Configuration
@ComponentScan(basePackages = { "org.srinivas.siteworks.rlrestserver.webconfig" })
public class RLRestServerMvcContextConfiguration extends WebMvcConfigurerAdapter {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {

	}

}
