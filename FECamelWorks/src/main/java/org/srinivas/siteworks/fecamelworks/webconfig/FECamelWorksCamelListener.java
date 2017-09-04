package org.srinivas.siteworks.fecamelworks.webconfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srinivas.siteworks.fecamelworks.FECamelWorksRoute;


public final class FECamelWorksCamelListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(FECamelWorksCamelListener.class);
	private static final CamelContext context = new DefaultCamelContext();

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public static CamelContext getContext() {
		return context;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Camel Context being Stopped for FECamelWorks");
		try {
			context.stop();
		} catch (Exception e) {
			log.info("Exception at FECamelWorksCamelListener", e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Camel context being started at FeCamelworks");
		try {
			context.addRoutes(new FECamelWorksRoute());
		} catch (Exception e) {
			log.info("Exception at FECamelWorksCamelListener", e);
		}
		try {
			context.start();
		} catch (Exception e) {
			log.info("Exception at FECamelWorksCamelListener", e);
		}
	}

}
