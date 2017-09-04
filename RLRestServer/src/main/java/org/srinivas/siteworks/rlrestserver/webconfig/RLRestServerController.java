/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.rlrestserver.webconfig;

import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.srinivas.siteworks.rlrestserver.RLRestServerRoute.*;

/**
 * The Class RLRestServerController.
 */
@RestController
public class RLRestServerController {
	private static final Logger log = LoggerFactory.getLogger(RLRestServerController.class);
	
	/**
	 * Rest page.
	 *
	 * @param request the request
	 * @param writer the writer
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/fireworks/getAll")
	public void restPage(HttpServletRequest request, Writer writer) throws Exception {
		log.info("RLRestServerController restPage RestPath:" + "fireworks/getAll");
		ProducerTemplate template = RLRestServerCamelListener.getContext().createProducerTemplate();
		String result = template.requestBody(CAMEL_ENDPOINT_DIRECT_START, "fireworks/getAll", String.class);
		writer.write(result);
	}

	/**
	 * Rest update page.
	 *
	 * @param request the request
	 * @param response the response
	 * @param writer the writer
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/fireworks/updatefirework", method = RequestMethod.POST)
	public void restUpdatePage(HttpServletRequest request, HttpServletResponse response, Writer writer) throws Exception {
		log.info("RLRestserver RLRestServerController restUpdatePage");
		String payload = IOUtils.toString(request.getReader());
		ProducerTemplate template = RLRestServerCamelListener.getContext().createProducerTemplate();
		String result = template.requestBodyAndHeader(CAMEL_ENDPOINT_DIRECT_START, "fireworks/updatefirework", "fireworkupdatekey", payload, String.class);
		log.info("resultprint at RLRestserver" + result);
		writer.write(result);
	}

}
