package org.srinivas.siteworks.fecamelworks;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class StringAggregationStrategy.
 */
public class StringAggregationStrategy implements AggregationStrategy {
	private static final Logger log = LoggerFactory.getLogger(StringAggregationStrategy.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.processor.aggregate.AggregationStrategy#aggregate(org.apache.camel.Exchange, org.apache.camel.Exchange)
	 */
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange == null) {
			return newExchange;
		}
		String oldBody = oldExchange.getIn().getBody(String.class);
		String newBody = newExchange.getIn().getBody(String.class);
		oldExchange.getIn().setBody(oldBody + "+" + newBody);
		log.info("Aggregated using StringAggregationStrategy");
		return oldExchange;
	}

}
