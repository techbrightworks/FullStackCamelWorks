package org.srinivas.siteworks.dbrestserver;

import java.io.StringWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srinivas.siteworks.dbrestserver.utilities.DbRestServerUtility;
import org.srinivas.siteworks.dbrestserver.utilities.FireWork;
import org.srinivas.siteworks.dbrestserver.utilities.FireWorksService;

/**
 * The Class FireWorksUpdateProcessor.
 */
public class FireWorksUpdateProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(FireWorksUpdateProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Processing with FireWorksUpdateProcessor");
		StringWriter stringWriter = new StringWriter();
		FireWorksService dataService = new FireWorksService();
		String source = exchange.getIn().getBody(String.class);
		log.info("The source is " + source);
		String id = StringUtils.substringBetween(source, "Update:", "{");
		log.info("The id  is " + id);
		String updateDescription = StringUtils.substringBetween(source, "{", "}");
		log.info("The change  is " + updateDescription);
		String description = (updateDescription.contains(",")) ? StringUtils.substringBetween(source, "description:", ",") : StringUtils.removeStart(updateDescription, "description:");
		FireWork firework = dataService.findFireWorkById(id);
		firework.setDescription(description);
		dataService.updateFireWork(firework);
		DbRestServerUtility.fireWorksToXML(dataService.getFireWork(String.valueOf(firework.getId())), stringWriter);
		exchange.getIn().setBody(stringWriter.toString());
	}

}
