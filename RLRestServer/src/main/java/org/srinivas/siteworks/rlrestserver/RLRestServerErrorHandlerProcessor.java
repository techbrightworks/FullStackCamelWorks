package org.srinivas.siteworks.rlrestserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.srinivas.siteworks.rlrestserver.RLRestServerRoute.*;

/**
 * The Class RLRestServerErrorHandlerProcessor.
 */
public class RLRestServerErrorHandlerProcessor implements Processor {
	public static final String RL_REST_SERVER_ERROR_WHILE_PROCESSING_REQUEST = "RLRestServer Error While Processing Request";
	public static final String RL_REST_SERVER_ERROR_WHILE_POST_PROCESSING_DB_RESPONSE = "RLRestServer Error While Post Processing DbResponse";
	public static final String RL_REST_SERVER_ERROR_WHILE_UPDATING_A_FIRE_WORK = "RLRestServer Error While Updating a FireWork";
	public static final String RL_REST_SERVER_ERROR_WHILE_FETCHING_LIST_OF_FIRE_WORKS = "RLRestServer Error While fetching List of  FireWorks";

	private static final Logger log = LoggerFactory.getLogger(RLRestServerErrorHandlerProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Exception rlexception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		log.error("RLRestServerErrorHandlerProcessor Logging:", rlexception);
		String RLRestServerStatus = exchange.getIn().getHeader(RL_ROUTE_STATUS, String.class);

		if (RLRestServerStatus.equalsIgnoreCase(RLFIREWORKSCOLLECTION)) {
			errorSetBody(exchange, RL_REST_SERVER_ERROR_WHILE_FETCHING_LIST_OF_FIRE_WORKS);
		} else if (RLRestServerStatus.equalsIgnoreCase(RLUPDATEFIREWORK)) {
			errorSetBody(exchange, RL_REST_SERVER_ERROR_WHILE_UPDATING_A_FIRE_WORK);
		} else if (RLRestServerStatus.equalsIgnoreCase(RLRESPONSEPROCESS)) {
			log.info("Exception Body" + exchange.getIn().getBody(String.class));
			errorSetBody(exchange, RL_REST_SERVER_ERROR_WHILE_POST_PROCESSING_DB_RESPONSE);
		} else {
			errorSetBody(exchange, RL_REST_SERVER_ERROR_WHILE_PROCESSING_REQUEST);
			if (rlexception != null) {
				errorSetBody(exchange, rlexception.getMessage());
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
			body = (StringUtils.isNotBlank(body)) ? body + System.getProperty("line.separator") + newBody : newBody;
		}
		exchange.getIn().setBody(body);
	}
}
