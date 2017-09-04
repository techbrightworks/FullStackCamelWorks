package org.srinivas.siteworks.rlrestserver.webconfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srinivas.siteworks.rlrestserver.RLRestServerRoute;

public final class RLRestServerCamelListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(RLRestServerCamelListener.class);
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

		log.info("Camel Context stopped at RLRestserver");
		try {
			context.stop();
		} catch (Exception e) {
			log.info("Exception at RLRestServerCamelListener", e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Camel Context started at RLRestserver");

		try {
			context.addRoutes(new RLRestServerRoute());
		} catch (Exception e) {
			log.info("Exception at RLRestServerCamelListener", e);
		}

		try {
			context.start();
		} catch (Exception e) {
			log.info("Exception at RLRestServerCamelListener", e);
		}

	}

}
