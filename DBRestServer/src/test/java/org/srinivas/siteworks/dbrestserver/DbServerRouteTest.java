package org.srinivas.siteworks.dbrestserver;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DbServerRouteTest.
 */
public class DbServerRouteTest extends CamelTestSupport {

	private static final Logger log = LoggerFactory.getLogger(DbServerRouteTest.class);

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
	 * Test DbRestServerRoute GetAll.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDbRestServerRoutegetALL() throws Exception {
		String result = template.requestBody("direct:dbfireworkscollection", null, String.class);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("<fws:fireworks"));
	}

	/**
	 * Test DbRestServerRoute update firework.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDbRestServerRouteUpdateFirework() throws Exception {
		String result = template.requestBody("direct:dbupdatefirework", "Update:789{description:fountains colour}", String.class);
		assertNotNull("Result is not Null", result);
		assertTrue("Result contains", result.contains("<fw:firework"));
	}

	/**
	 * Test DbRestServerRoute direct error.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDbRestServerRouteDirectError() throws Exception {
		String errorString = "Error Test";
		String result = template.requestBodyAndHeader("direct:error", errorString, "DbRouteStatus", "dbupdatefirework", String.class);
		assertNotNull("Result is not Null", result);
		log.info(result);
		assertTrue("Result contains", result.contains("DbServer Error While Updating a FireWork"));
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.test.junit4.CamelTestSupport#createRouteBuilder()
	 */
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new DbServerRoute();
	}

}
