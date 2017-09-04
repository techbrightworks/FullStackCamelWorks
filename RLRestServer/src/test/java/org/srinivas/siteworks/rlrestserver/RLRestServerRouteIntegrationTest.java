package org.srinivas.siteworks.rlrestserver;

import static org.junit.Assert.*;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.engine.header.HeaderConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RLRestServerRouteIntegrationTest.
 */
public class RLRestServerRouteIntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(RLRestServerRouteIntegrationTest.class);
	private final static int port = 9091;
	private static final String FIREWORKS_COLLECTIONS_URL = "restlet:http://localhost:" + port + "/RLRestServer/fireworks/getAll?restletMethods=GET";
	private static final String FIREWORK_UPDATE_URL = "restlet:http://localhost:" + port + "/RLRestServer/fireworks/updatefirework?restletMethod=POST";
	RouteBuilder getALLRouteClient;
	RouteBuilder updateFireWorkRouteClient;

	/**
	 * Sets up before the test.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		getALLRouteClient = new RouteBuilder() {
			public void configure() {
				from("direct:a").to(FIREWORKS_COLLECTIONS_URL);
			}
		};

		updateFireWorkRouteClient = new RouteBuilder() {
			public void configure() {
				from("direct:b").setHeader(HeaderConstants.HEADER_CONTENT_TYPE, constant(MediaType.TEXT_PLAIN)).to(FIREWORK_UPDATE_URL);
			}
		};

	}
	
	/**
	 * Teardown.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void teardown() throws Exception {
		getALLRouteClient = null;
		updateFireWorkRouteClient = null;
	}

	/**
	 * Update firework test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateFireWorkTest() throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(updateFireWorkRouteClient);
		ProducerTemplate template = context.createProducerTemplate();
		context.start();
		String result = (String) template.requestBody("direct:b", "Update:789{description:fountains colour}", String.class);
		log.info("The result is" + result);
		context.stop();
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("fountains colour"));
	}

	/**
	 * GetAll fireworks test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getAllTest() throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(getALLRouteClient);
		ProducerTemplate template = context.createProducerTemplate();
		context.start();
		String result = (String) template.requestBody("direct:a", null, String.class);
		log.info("The result is" + result);
		context.stop();
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("FireWorks Search"));
	}

}
