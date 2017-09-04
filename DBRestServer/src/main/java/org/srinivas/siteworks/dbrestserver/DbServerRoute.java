package org.srinivas.siteworks.dbrestserver;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * The Class DbServerRoute.
 */
public class DbServerRoute extends RouteBuilder {

	public static final String CAMEL_ENDPOINT_DIRECT_DBUPDATEFIREWORK = "direct:dbupdatefirework";
	public static final String CAMEL_ENDPOINT_DIRECT_DBFIREWORKSCOLLECTION = "direct:dbfireworkscollection";
	public static final int DBRESTSERVER_PORT_NUMBER_8181 = 8181;
	public static final String DOMAIN_LOCALHOST = "localhost";
	public static final String CAMEL_ENDPOINT_DIRECT_ERROR = "direct:error";
	public static final String DBUPDATEFIREWORK = "dbupdatefirework";
	public static final String DBFIREWORKSCOLLECTION = "dbfireworkscollection";
	public static final String DB_ROUTE_STATUS = "DbRouteStatus";

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		onException(Exception.class).onWhen(header(DB_ROUTE_STATUS).contains(DBFIREWORKSCOLLECTION)).to(CAMEL_ENDPOINT_DIRECT_ERROR).handled(true).end();
		onException(Exception.class).onWhen(header(DB_ROUTE_STATUS).contains(DBUPDATEFIREWORK)).to(CAMEL_ENDPOINT_DIRECT_ERROR).handled(true).end();
		onException(Exception.class).handled(true).transform().constant("Sorry DBRestServer Could Not Process Due to Errors").end();

		restConfiguration().component("undertow").host(DOMAIN_LOCALHOST)
				.port(DBRESTSERVER_PORT_NUMBER_8181).bindingMode(RestBindingMode.auto)
				.componentProperty("matchOnUriPrefix", "true")
				.dataFormatProperty("prettyPrint", "true").apiContextPath("/api-doc")
				.apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
				.apiProperty("cors", "true");

		rest("/fireworks/getAll").description("Servlet for Get All Fireworks").get()
				.description("Get Request of Servlet for Get All Fireworks and It Produces XML")
				.outType(String.class).produces("appication/xml").route()
				.to(CAMEL_ENDPOINT_DIRECT_DBFIREWORKSCOLLECTION).endRest().post()
				.description("POST Request of Servlet for Get All Fireworks and It Produces XML")
				.outType(String.class).produces("appication/xml").route()
				.to(CAMEL_ENDPOINT_DIRECT_DBFIREWORKSCOLLECTION);
		rest("/fireworks/updatefirework").description("Servlet for Update Firework").post()
				.description("Get Request of Servlet for Update Firework and It Produces XML")
				.outType(String.class).produces("text/plain").route()
				.to(CAMEL_ENDPOINT_DIRECT_DBUPDATEFIREWORK);

		from(CAMEL_ENDPOINT_DIRECT_DBFIREWORKSCOLLECTION)
				.setHeader(DB_ROUTE_STATUS, constant(DBFIREWORKSCOLLECTION))
				.process(new FireWorksListProcessor()).log("Message ${body}");
		from(CAMEL_ENDPOINT_DIRECT_DBUPDATEFIREWORK)
				.setHeader(DB_ROUTE_STATUS, constant(DBUPDATEFIREWORK))
				.process(new FireWorksUpdateProcessor()).log("Message ${body}");
		from(CAMEL_ENDPOINT_DIRECT_ERROR).process(new DbServerErrorHandlerProcessor()).log(
				"Message ${body}");
	}

}
