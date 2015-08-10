package com.mydomain;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.UserSessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class RouterVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> startFuture) throws Exception {
		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		router.route().handler(CookieHandler.create());
		router.route().handler(
				SessionHandler.create(LocalSessionStore.create(vertx)));
		AuthProvider ap = new MyAuthProvier();
		router.route().handler(UserSessionHandler.create(ap));

		AuthHandler basicAuthHandler = BasicAuthHandler.create(ap);

		router.route("/private/*").handler(basicAuthHandler);
		router.route("/private/*").handler(new Handler<RoutingContext>() {
			@Override
			public void handle(RoutingContext rc) {
				System.out.println("Handler: " + rc.user().principal());
				rc.response().end("Done");
			}
		});
		
		router.options("/Services/rest/user/register").handler(new CORSHandler());
		router.get("/services/users/:id").handler(new UserLoader());
		router.post("/Services/rest/user/register").handler(new UserPersister());

		server.requestHandler(router::accept).listen(9090);
		System.out.println("Thread Router Start: "
				+ Thread.currentThread().getId());
		System.out.println("STARTED ROUTER");
		startFuture.complete(); 
	}
	
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.factory.vertx(options);
		vertx.deployVerticle("com.mydomain.RouterVerticle");
		}
}