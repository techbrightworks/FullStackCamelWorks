package org.srinivas.siteworks.dbrestserver.webconfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srinivas.siteworks.dbrestserver.DbServerRoute;


public final class DbServerCamelListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(DbServerCamelListener.class);
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
		log.info("Finished with the camel at DBRestServer");
		try {
			context.stop();
		} catch (Exception e) {
			log.info("Exception at DbServerCamelListener", e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Started camel Context camel at DBRestServer");
		try {
			context.addRoutes(new DbServerRoute());
		} catch (Exception e) {
			log.info("Exception at DbServerCamelListener", e);
		}

		try {
			context.start();
		} catch (Exception e) {
			log.info("Exception at DbServerCamelListener", e);
		}
	}
}
