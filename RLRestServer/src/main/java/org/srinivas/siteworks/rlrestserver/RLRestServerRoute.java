package org.srinivas.siteworks.rlrestserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.util.Series;

/**
 * The Class RLRestServerRoute.
 */
public class RLRestServerRoute extends RouteBuilder {
	public static final String CAMEL_ENDPOINT_DIRECT_START = "direct:start";
	private static final String CAMEL_ENDPOINT_DIRECT_RLFIREWORKSCOLLECTION = "direct:RLfireworkscollection";
	public static final String CAMEL_ENDPOINT_DIRECT_RLUPDATEFIREWORK = "direct:RLupdatefirework";
	public static final String CAMEL_ENDPOINT_DIRECT_RLRESPONSEPROCESS = "direct:RLresponseprocess";
	public static final String CAMEL_ENDPOINT_DIRECT_ERROR = "direct:error";
	public static final int PORT_NUMBER_8180 = 8180;
	public static final String DOMAIN_LOCALHOST = "localhost";
	public static final String RLRESPONSEPROCESS = "RLresponseprocess";
	public static final String RLUPDATEFIREWORK = "RLupdatefirework";
	public static final String RLFIREWORKSCOLLECTION = "RLfireworkscollection";
	public static final String RL_ROUTE_STATUS = "RLRouteStatus";
	
	
	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		onException(Exception.class).onWhen(header(RL_ROUTE_STATUS).contains(RLFIREWORKSCOLLECTION)).handled(true).to(CAMEL_ENDPOINT_DIRECT_ERROR).end();
		onException(Exception.class).onWhen(header(RL_ROUTE_STATUS).contains(RLUPDATEFIREWORK)).handled(true).to(CAMEL_ENDPOINT_DIRECT_ERROR).end();
		onException(Exception.class).onWhen(header(RL_ROUTE_STATUS).contains(RLRESPONSEPROCESS)).to(CAMEL_ENDPOINT_DIRECT_ERROR).handled(true).end();
		onException(Exception.class).handled(true).transform().constant("Sorry RLRestServer Could Not Process Due to Errors").end();

		restConfiguration().component("restlet").host(DOMAIN_LOCALHOST).port(PORT_NUMBER_8180)
				.bindingMode(RestBindingMode.auto).dataFormatProperty("prettyPrint", "true")
				.apiContextPath("/api-doc").apiProperty("api.title", "User API")
				.apiProperty("api.version", "1.2.3").apiProperty("cors", "true");

		rest("/fireworks/getAll").description("Restlet for Get All FireWorks").get()
				.description("Get Request of Restlet for Get All FireWorks and It Produces XML")
				.outType(String.class).produces("text/plain").route()
				.to(CAMEL_ENDPOINT_DIRECT_RLFIREWORKSCOLLECTION).endRest().post()
				.description("POST Request of Restlet for Get All FireWorks and It Produces XML")
				.outType(String.class).produces("text/plain").route()
				.to(CAMEL_ENDPOINT_DIRECT_RLFIREWORKSCOLLECTION);
		rest("/fireworks/updatefirework").description("Restlet for Update Firework").post()
				.description("Post Request of Restlet for Update Firework and It Produces XML")
				.outType(String.class).produces("text/plain").route().to(CAMEL_ENDPOINT_DIRECT_RLUPDATEFIREWORK);

		from(CAMEL_ENDPOINT_DIRECT_START).choice().when(body().isEqualToIgnoreCase("fireworks/getAll"))
				.to("restlet:http://localhost:8180/fireworks/getAll?restletMethods=GET")
				.when(body().isEqualToIgnoreCase("fireworks/updatefirework"))
				.to("restlet:http://localhost:8180/fireworks/updatefirework?restletMethod=POST")
				.otherwise().transform(simple("${body}: does not have a corresponding Restlet"))
				.end();

		from(CAMEL_ENDPOINT_DIRECT_RLFIREWORKSCOLLECTION)
				.setHeader(RL_ROUTE_STATUS, constant(RLFIREWORKSCOLLECTION))
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						exchange.getIn().setHeader("org.restlet.http.headers",
								new Series<Header>(Header.class));
					}
				}).to("restlet:http://localhost:8181/fireworks/getAll?restletMethods=GET")
				.to(CAMEL_ENDPOINT_DIRECT_RLRESPONSEPROCESS).log("Message ${body}");

		from(CAMEL_ENDPOINT_DIRECT_RLUPDATEFIREWORK)
				.setHeader(RL_ROUTE_STATUS, constant(RLUPDATEFIREWORK))
				.setHeader(HeaderConstants.HEADER_CONTENT_TYPE, constant(MediaType.TEXT_PLAIN))
				.transform()
				.simple("${in.header[fireworkupdatekey]}")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						exchange.getIn().setHeader("org.restlet.http.headers",
								new Series<Header>(Header.class));
					}
				}).to("restlet:http://localhost:8181/fireworks/updatefirework?restletMethod=POST")
				.log("Message in RLupdatefirework finsished to dbserver")
				.to(CAMEL_ENDPOINT_DIRECT_RLRESPONSEPROCESS).log("Message ${body}");

		from(CAMEL_ENDPOINT_DIRECT_RLRESPONSEPROCESS).setHeader(RL_ROUTE_STATUS, constant(RLRESPONSEPROCESS))
				.log("Message in RLresponseprocess moving to xquery").convertBodyTo(String.class)
				.to("xquery:removeNameSpaces.xqy").convertBodyTo(String.class)
				.log("Message ${body}").setHeader("sequence")
				.groovy("resource:classpath:Mygroovy.groovy");

		from(CAMEL_ENDPOINT_DIRECT_ERROR).process(new RLRestServerErrorHandlerProcessor())
				.log("Message ${body}");

	}

}
