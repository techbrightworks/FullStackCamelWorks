package org.srinivas.siteworks.fecamelworks;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FECamelWorksRouteTest.
 */
public class FECamelWorksRouteTest extends CamelTestSupport {
	private static final Logger log = LoggerFactory.getLogger(FECamelWorksRouteTest.class);

	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	 /* (non-Javadoc)
 	 * @see org.apache.camel.test.junit4.CamelTestSupport#isCreateCamelContextPerClass()
 	 */
 	@Override
	    public boolean isCreateCamelContextPerClass() {
	        return true;
	 }

	/**
	 * Test fecamelworks route update fire work.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testFECamelWorksRouteUpdateFireWork() throws Exception {
		final String str = "<firework><category>Fountains</category><description>fountains colour</description><id>789</id><name>Small Fountain</name></firework>";
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				weaveByToString("To[http4://localhost:8080/RLRestServer/fireworks/updatefirework]").replace().transform(constant(str));
			}
		});

		String result = template.requestBody("direct:process", "Update:789{description:fountains colour}", String.class);
		log.info("result is" + result);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("<firework>"));
	}

	/**
	 * Test fecamelworks route get all.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testFECamelWorksRouteGetAll() throws Exception {
		final String str = "<fireworks><firework><id>123</id><name>Sparkler200</name></firework><firework><id>265</id><name>Ground Swirls</name></firework><firework><id>456</id><name>Rocket Absolute</name></firework><firework><id>789</id><name>Small Fountain</name></firework></fireworks>";
		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				weaveByToString("To[http4://localhost:8080/RLRestServer/fireworks/getAll]").replace().transform(constant(str));
			}
		});
		String result = template.requestBody("direct:process", "getAll", String.class);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("<fireworks>"));
	}

	/**
	 * Test FECamelWorksRoute direct error.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testFECamelWorksRouteDirectError() throws Exception {
		String str = "Error Test";
		String result = template.requestBodyAndHeader("direct:error", str, "FERouteStatus", "FEupdatefirework", String.class);
		assertNotNull("Result is not Null", result);
		log.info(result);
		assertTrue("Result contains", result.contains("FECamelWorks Error While Updating a FireWork"));
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.test.junit4.CamelTestSupport#createRouteBuilder()
	 */
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new FECamelWorksRoute();
	}

}
