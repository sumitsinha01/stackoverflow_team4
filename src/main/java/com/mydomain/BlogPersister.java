package com.mydomain;

import java.io.IOException;
import java.util.Date;

import org.mongodb.morphia.Datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.infra.ServicesFactory;
import com.mysocial.model.Blog;
import com.mysocial.model.BlogDTO;
import com.mysocial.model.User;
import com.mysocial.model.UserDTO;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;

public class BlogPersister implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext context) {
		System.out.println("Thread BlogPersister: "
				+ Thread.currentThread().getId());
		// This handler will be called for every request
		HttpServerResponse response = context.response();
		context.request().bodyHandler(new Handler<Buffer>() {
			public void handle(Buffer buf) {
				String json = buf.toString("UTF-8");
				System.out.println("JSON= " + json);
				ObjectMapper mapper = new ObjectMapper();
				BlogDTO dto = null;

				try {
					dto = mapper.readValue(json, BlogDTO.class);
					System.out.println("DTO Class= " + dto);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Session session = context.session();
				io.vertx.ext.auth.User user = session.get("user");
				JsonObject jason = user.principal();
				String username = jason.getString("username");
				String first = jason.getString("first");
				String last = jason.getString("last");
				Date date = new Date();
				Blog b = dto.toModel();
				b.setDate(date);
				b.setFirst(first);
				b.setLast(last);
				b.setUserName(username);
				Datastore dataStore = ServicesFactory.getMongoDB();
				dataStore.save(b);
				response.setStatusCode(204).end("Data saved");
			};
		});
		
		
		
		
	}

}
