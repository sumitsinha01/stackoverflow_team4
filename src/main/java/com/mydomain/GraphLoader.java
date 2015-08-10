package com.mydomain;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

class GraphLoader implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext arg0) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		File f = new File("/Users/maruthir/Documents/Training/neo4jdb");
		GraphDatabaseService db = dbFactory.newEmbeddedDatabase(f);
		try (Transaction tx = db.beginTx()) {
			// Perform DB operations
			tx.success();
		}
	}
}