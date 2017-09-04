
package org.srinivas.siteworks.dbrestserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.srinivas.siteworks.dbrestserver.DbServerRoute.*;

/**
 * The Class DbServerErrorHandlerProcessor.
 */
public class DbServerErrorHandlerProcessor implements Processor {
	public static final String DB_SERVER_ERROR_WHILE_PROCESSING_REQUEST = "DbServer Error While Processing Request";
	public static final String DB_SERVER_ERROR_WHILE_UPDATING_A_FIRE_WORK = "DbServer Error While Updating a FireWork";
	public static final String DB_SERVER_ERROR_WHILE_FETCHING_LIST_OF_FIRE_WORKS = "DbServer Error While fetching List of  FireWorks";
	public static final Logger log = LoggerFactory.getLogger(DbServerErrorHandlerProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Exception dbexception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		log.error("DbServerErrorHandlerProcessor Logging:", dbexception);
		String DbRestServerStatus = exchange.getIn().getHeader(DB_ROUTE_STATUS, String.class);

		if (DbRestServerStatus.equalsIgnoreCase(DBFIREWORKSCOLLECTION)) {
			errorSetBody(exchange, DB_SERVER_ERROR_WHILE_FETCHING_LIST_OF_FIRE_WORKS);
		} else if (DbRestServerStatus.equalsIgnoreCase(DBUPDATEFIREWORK)) {
			errorSetBody(exchange, DB_SERVER_ERROR_WHILE_UPDATING_A_FIRE_WORK);
			errorSetBody(exchange, exchange.getIn().getBody(String.class));
		} else {
			errorSetBody(exchange, DB_SERVER_ERROR_WHILE_PROCESSING_REQUEST);
			if (dbexception != null) {
				errorSetBody(exchange, dbexception.getMessage());
			}
		}
	}

	/**
	 * Error set camel body.
	 *
	 * @param exchange the exchange
	 * @param newBody the new body
	 */
	private void errorSetBody(Exchange exchange, String newBody) {
		String body = exchange.getIn().getBody(String.class);
		if (newBody != null) {
			body = (StringUtils.isNotBlank(body)) ? body + System.getProperty("line.separator")
					+ newBody : newBody;
		}
		exchange.getIn().setBody(body);
	}

}
