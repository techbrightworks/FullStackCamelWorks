/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.fecamelworks.webconfig;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Class FECamelWorksController.
 */
@Controller
public class FECamelWorksController {
	public static final String HTML_BREAK_TAG = "<br/>";
	private static final String NEWLINE_REGEX = "\\r\\n|\\n";
	private static final Logger log = LoggerFactory.getLogger(FECamelWorksController.class);

	/**
	 * Camelworks page.
	 *
	 * @param model the model
	 * @return the string
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/camelworks.mvc", method = RequestMethod.GET)
	public String camelWorksPage(Model model) throws Exception {
		log.info("CamelWorksController");
		ProducerTemplate template = org.srinivas.siteworks.fecamelworks.webconfig.FECamelWorksCamelListener.getContext().createProducerTemplate();
		String result = template.requestBody("direct:process", "getAll&&Update:789{description:fountains colour}", String.class);
		result = result.replaceAll(NEWLINE_REGEX, HTML_BREAK_TAG);
		log.info("camelWorksPage Result is" + result);
		model.addAttribute("message", result);
		return "camelworks";
	}
}
