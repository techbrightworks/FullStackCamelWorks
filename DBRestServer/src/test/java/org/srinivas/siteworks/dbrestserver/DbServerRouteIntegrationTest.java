package org.srinivas.siteworks.dbrestserver;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DbServerRouteIntegrationTest.
 */
public class DbServerRouteIntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(DbServerRouteIntegrationTest.class);
	private final static int port = 8181;

	private static final String FIREWORKS_COLLECTIONS_URL = "http://localhost:" + port + "/fireworks/getAll";
	private static final String FIREWORK_UPDATE_URL = "http://localhost:" + port + "/fireworks/updatefirework";
	RouteBuilder getALLRouteClient;
	RouteBuilder updateFireWorkRouteClient;

	/**
	 * Sets up Before Test.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		getALLRouteClient = new RouteBuilder() {
			public void configure() {
				from("direct:a").setHeader(Exchange.HTTP_METHOD, constant("GET")).to(FIREWORKS_COLLECTIONS_URL);
			}
		};
		updateFireWorkRouteClient = new RouteBuilder() {
			public void configure() {
				from("direct:b").transform().constant("Update:789{description:fountains colour}").setHeader(Exchange.HTTP_METHOD, constant("POST")).to(FIREWORK_UPDATE_URL);
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
	 * Update a firework test.
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
		assertTrue("Result contains", result.contains("<fw:firework"));

	}

	/**
	 * GetAllFireworks test.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void getAllTest() throws Exception {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("cubekey", "Update:789{description:fountains colour}");
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(getALLRouteClient);
		ProducerTemplate template = context.createProducerTemplate();
		context.start();
		String result = (String) template.requestBody("direct:a", null, String.class);
		log.info("The result is" + result);
		context.stop();
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("<fws:fireworks"));
	}

}
