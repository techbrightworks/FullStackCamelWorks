package org.srinivas.siteworks.rlrestserver;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RLRestServerRouteTest.
 */
public class RLRestServerRouteTest extends CamelTestSupport {
	private static final Logger log = LoggerFactory.getLogger(RLRestServerRouteTest.class);

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
	 * Test RLRestServerRoute get all fireworks.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRLRestServerRoutegetALL() throws Exception {
		String str = "<fireworks><firework><id>123</id><name>Sparkler200</name></firework><firework><id>265</id><name>Ground Swirls</name></firework><firework><id>456</id><name>Rocket Absolute</name></firework><firework><id>789</id><name>Small Fountain</name></firework></fireworks>";
		context.getRouteDefinitions().get(2).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				weaveByToString(".*localhost:8181/fireworks/getAll.*").replace().transform(constant(str));
			}
		});
		String result = template.requestBody("direct:start", "fireworks/getAll", String.class);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("FireWorks Search"));
	}

	/**
	 * Test RLRestServerRoute update firework.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRLRestServerRouteUpdateFirework() throws Exception {
		String str = "<firework><category>Fountains</category><description>fountains colour</description><id>789</id><name>Small Fountain</name></firework>";
		context.getRouteDefinitions().get(2).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				weaveByToString(".*localhost:8181/fireworks/updatefirework.*").replace().transform(constant(str));
			}
		});
		String result = template.requestBodyAndHeader("direct:start", "fireworks/updatefirework", "cubekey", "Update:789{description:fountains colour}", String.class);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("fountains colour"));
	}

	/**
	 * Test RLRestServerRoute route response processing.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRLRestServerRouteResponseProcessing() throws Exception {
		String str = "<fireworks><firework><id>123</id><name>Sparkler200</name></firework><firework><id>265</id><name>Ground Swirls</name></firework><firework><id>456</id><name>Rocket Absolute</name></firework><firework><id>789</id><name>Small Fountain</name></firework></fireworks>";
		String result = template.requestBody("direct:RLresponseprocess", str, String.class);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("FireWorks Search"));
	}

	/**
	 * Test RLRestServerRoute direct error.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRLRestServerRouteDirectError() throws Exception {
		String str = "Error Test";
		String result = template.requestBodyAndHeader("direct:error", str, "RLRouteStatus", "RLupdatefirework", String.class);
		assertNotNull("Result is not Null", result);
		log.info(result);
		assertTrue("Result contains", result.contains("RLRestServer Error While Updating a FireWork"));
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.test.junit4.CamelTestSupport#createRouteBuilder()
	 */
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RLRestServerRoute();
	}

}
