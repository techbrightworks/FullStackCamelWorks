package org.srinivas.siteworks.fecamelworks;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.srinivas.siteworks.fecamelworks.FECamelWorksRoute.*;

/**
 * The Class FECamelWorksErrorHandlerProcessor.
 */
public class FECamelWorksErrorHandlerProcessor implements Processor {
	public static final String FE_CAMEL_WORKS_ERROR_WHILE_PROCESSING_REQUEST = "FECamelWorks Error While Processing Request";
	public static final String FE_CAMEL_WORKS_ERROR_WHILE_UPDATING_A_FIRE_WORK = "FECamelWorks Error While Updating a FireWork";
	public static final String FE_CAMEL_WORKS_ERROR_WHILE_FETCHING_LIST_OF_FIRE_WORKS = "FECamelWorks Error While fetching List of  FireWorks";
	private static final Logger log = LoggerFactory.getLogger(FECamelWorksErrorHandlerProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Exception feexception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		log.error("FECamelWorksErrorHandlerProcessor Logging:", feexception);
		String FECamelWorksStatus = exchange.getIn().getHeader(FE_ROUTE_STATUS, String.class);
		if (FECamelWorksStatus.equalsIgnoreCase(FEFIREWORKSCOLLECTION)) {
			errorSetBody(exchange, FE_CAMEL_WORKS_ERROR_WHILE_FETCHING_LIST_OF_FIRE_WORKS);
		} else if (FECamelWorksStatus.equalsIgnoreCase(FEUPDATEFIREWORK)) {
			errorSetBody(exchange, FE_CAMEL_WORKS_ERROR_WHILE_UPDATING_A_FIRE_WORK);
		} else {
			errorSetBody(exchange, FE_CAMEL_WORKS_ERROR_WHILE_PROCESSING_REQUEST);
			if (feexception != null) {
				errorSetBody(exchange, feexception.getMessage());
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
