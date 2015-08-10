package com.mydomain;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;


public class CORSHandler  implements Handler<RoutingContext> {
	public void handle(RoutingContext routingContext) {
		System.out.println("Thread CORSHandler: "
				+ Thread.currentThread().getId());
		// This handler will be called for every request
		HttpServerResponse response = routingContext.response();
		response.putHeader("Access-Control-Allow-Origin", "*");
		response.putHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		response.putHeader("Access-Control-Allow-Headers", "*");
		response.putHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,accept,x-requested-with"); 
		response.end(); 
	}
}
