/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.rlrestserver.webconfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * The Class RLRestServerWebInitializer.
 */
public class RLRestServerWebInitializer implements WebApplicationInitializer {

	private static final Logger logger = LoggerFactory.getLogger(RLRestServerWebInitializer.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(ServletContext container) {
		logger.info("Started to pickup the annotated classes at RLRestServerWebInitializer");
		startServlet(container);
	}

	/**
	 * Start servlet.
	 *
	 * @param container the container
	 */
	private void startServlet(final ServletContext container) {
		WebApplicationContext dispatcherContext = registerContext(RLRestServerMvcContextConfiguration.class);
		DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
		container.addListener(new ContextLoaderListener(dispatcherContext));
		container.addListener(new RLRestServerCamelListener());
		ServletRegistration.Dynamic dispatcher;
		dispatcher = container.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");
	}

	/**
	 * Register context.
	 *
	 * @param annotatedClasses the annotated classes
	 * @return the web application context
	 */
	private WebApplicationContext registerContext(final Class<?>... annotatedClasses) {
		logger.info("Using AnnotationConfigWebApplicationContext createContext");
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}

}
