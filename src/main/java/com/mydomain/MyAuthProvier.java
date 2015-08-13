package com.mydomain;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.mydomain.infra.ServicesFactory;
import com.mysocial.model.User;

class MyAuthProvier implements AuthProvider {
	private String first;
	private String last;
	private String username;

	@Override
	public void authenticate(JsonObject json,
			Handler<AsyncResult<io.vertx.ext.auth.User>> handler) {
		System.out.println("Authenticating users with: " + json);
		
		AsyncResult<io.vertx.ext.auth.User> result = new AsyncResult<io.vertx.ext.auth.User>() {
			public boolean succeeded() {
				
				boolean auth = userAuthentication(json.getString("username"),json.getString("password"));
				return auth;
//				return json.getString("username").equals("admin")
//						&& json.getString("password").equals("admin123");
				
			}
			
			

			public io.vertx.ext.auth.User result() {
				return new io.vertx.ext.auth.User() {
					public void setAuthProvider(AuthProvider provider) {
						System.out
								.println("Setting auth provider: " + provider);
					}

					public JsonObject principal() {
						Map<String, Object> dataMap = new HashMap<>();
						dataMap.put("username", json.getString("username"));
						dataMap.put("first", json.getString("first"));
						dataMap.put("last", json.getString("last"));
						JsonObject obj = new JsonObject(dataMap);
						return obj;
					}

					public io.vertx.ext.auth.User isAuthorised(String url,
							Handler<AsyncResult<Boolean>> handler) {
						System.out.println("Is authorized call: " + url);
						return this;
					}

					public io.vertx.ext.auth.User clearCache() {
						return null;
					}
				};
			}

			public boolean failed() {
				return !(json.getString("username").equals("admin") && json
						.getString("password").equals("admin123"));
			}

			public Throwable cause() {
				return null;
			}
		};
		handler.handle(result);
	}

	public boolean userAuthentication(String uname,String pwd) {
		
		boolean authenticated = false;
		Datastore dataStore = ServicesFactory.getMongoDB();
		List<User> users = dataStore.createQuery(User.class).field("userName")
				.equal(uname).asList();
		if (users.size() == 0) {
			System.out.println("User does not exist");
		}
		else {
			System.out.println(users.get(0).getUserName());
			if (users.get(0).getPassword().equals(pwd)){
				System.out.println("username-->" +uname + "password-->" + pwd + "matched");
				first = users.get(0).getFirst();
				last = users.get(0).getLast();
				username = uname;
				authenticated = true;
			}
		}
		
		
		return authenticated;
	}
}
