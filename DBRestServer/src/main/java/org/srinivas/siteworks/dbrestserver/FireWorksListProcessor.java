package org.srinivas.siteworks.dbrestserver;

import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srinivas.siteworks.dbrestserver.utilities.DbRestServerUtility;
import org.srinivas.siteworks.dbrestserver.utilities.FireWork;
import org.srinivas.siteworks.dbrestserver.utilities.FireWorks;
import org.srinivas.siteworks.dbrestserver.utilities.FireWorksService;

/**
 * The Class FireWorksListProcessor.
 */
public class FireWorksListProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(FireWorksListProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Processing with FireWorksListProcessor");
		StringWriter stringWriter = new StringWriter();
		FireWorksService dataService = new FireWorksService();
		FireWorks fireworks = new FireWorks();
		fireworks.setFireworks(new ArrayList<FireWork>(dataService.listFireWorks()));
		DbRestServerUtility.fireWorksCollectionToXML(fireworks, stringWriter);
		exchange.getIn().setBody(stringWriter.toString());
	}
}
