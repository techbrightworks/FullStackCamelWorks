package org.srinivas.siteworks.fecamelworks;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * The Class FECamelWorksRoute.
 */
public class FECamelWorksRoute extends RouteBuilder {

	public static final String CAMEL_ENDPOINT_DIRECT_ERROR = "direct:error";
	public static final String FEUPDATEFIREWORK = "FEupdatefirework";
	public static final String FEFIREWORKSCOLLECTION = "FEfireworkscollection";
	public static final String FE_ROUTE_STATUS = "FERouteStatus";

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		onException(Exception.class).onWhen(header(FE_ROUTE_STATUS).contains(FEFIREWORKSCOLLECTION)).to(CAMEL_ENDPOINT_DIRECT_ERROR).handled(true).end();
		onException(Exception.class).onWhen(header(FE_ROUTE_STATUS).contains(FEUPDATEFIREWORK)).to(CAMEL_ENDPOINT_DIRECT_ERROR).handled(true).end();
		onException(Exception.class).handled(true).transform().constant("Sorry FECameWorks Could Not Process Due to Errors").end();

		from("direct:process")
				.split(bodyAs(String.class).tokenize("&&"), new StringAggregationStrategy())
				.choice()
				.when(body().contains("getAll"))
				.setHeader(FE_ROUTE_STATUS, constant(FEFIREWORKSCOLLECTION))
				.setHeader(Exchange.HTTP_METHOD,
						constant(org.apache.camel.component.http4.HttpMethods.GET))
				.to("http4://localhost:8080/RLRestServer/fireworks/getAll")
				.when(body().contains("Update:"))
				.setHeader(FE_ROUTE_STATUS, constant(FEUPDATEFIREWORK))
				.log("Message ${body}")
				.setHeader(Exchange.HTTP_METHOD,
						constant(org.apache.camel.component.http4.HttpMethods.POST))
				.to("http4://localhost:8080/RLRestServer/fireworks/updatefirework")
				.convertBodyTo(String.class).otherwise()
				.transform(simple("${body}: does not have a corresponding Service")).end()
				.convertBodyTo(String.class).log("Message ${body}").end().to("mock:result");

		from(CAMEL_ENDPOINT_DIRECT_ERROR).process(new FECamelWorksErrorHandlerProcessor())
				.log("Message ${body}");
	}

}
