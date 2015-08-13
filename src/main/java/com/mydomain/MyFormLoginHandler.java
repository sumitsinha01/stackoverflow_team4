package com.mydomain;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysocial.model.User;
import com.mysocial.model.UserDTO;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.FormLoginHandler;

public class MyFormLoginHandler implements FormLoginHandler {

  private AuthProvider authProvider;

  static FormLoginHandler create(AuthProvider authProvider) {
	  return new MyFormLoginHandler(authProvider);
  }
  
  public MyFormLoginHandler(AuthProvider authProvider) {
    this.authProvider = authProvider;
  }

  public MyFormLoginHandler() {
	// TODO Auto-generated constructor stub
  }

@Override
  public void handle(RoutingContext context) {
 
	HttpServerRequest req = context.request();
    if (req.method() != HttpMethod.POST) {
        context.fail(405); // Must be a POST
    } 
    req.bodyHandler(new Handler<Buffer>() {
		public void handle(Buffer buf) {
			String json = buf.toString("UTF-8");
			System.out.println("JSON= " + json);
			ObjectMapper mapper = new ObjectMapper();
			UserDTO dto = null;
			try {
				dto = mapper.readValue(json, UserDTO.class);
				System.out.println("DTO Class= " + dto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			User u = dto.toModel();
	        Session session = context.session();
	        JsonObject authInfo = new JsonObject().put("username", u.getUserName()).put("password", u.getPassword());
	        authProvider.authenticate(authInfo, res -> {
	          if (res.succeeded()) {
	            io.vertx.ext.auth.User user = res.result();
	            context.setUser(user);
	            System.out.println("Logging Success");
	            req.response().setStatusCode(204).end("user Login success");
//	            if (session != null) {
//	              String returnURL = session.remove(returnURLParam);
//	              if (returnURL != null) {
//	                // Now redirect back to the original url
//	                doRedirect(req.response(), returnURL);
//	                return;
//	              }
//	            }
//	            // Either no session or no return url
//	            if (directLoggedInOKURL != null) {
//	              // Redirect to the default logged in OK page - this would occur
//	              // if the user logged in directly at this URL without being redirected here first from another
//	              // url
//	              doRedirect(req.response(), directLoggedInOKURL);
//	            } else {
//	              // Just show a basic page
//	              req.response().end(DEFAULT_DIRECT_LOGGED_IN_OK_PAGE);
//	            }
	          } else {
	        	  System.out.println("Login Failed");
	            context.fail(403);  // Failed login
	          }
	        });
	      }
    });
//      MultiMap params = req.formAttributes();
//      String username = params.get(usernameParam);
//      String password = params.get(passwordParam);
//      if (username == null || password == null) {
//        log.warn("No username or password provided in form - did you forget to include a BodyHandler?");
//        context.fail(400);
//      } else {
//        Session session = context.session();
//        JsonObject authInfo = new JsonObject().put("username", username).put("password", password);
//        authProvider.authenticate(authInfo, res -> {
//          if (res.succeeded()) {
//            io.vertx.ext.auth.User user = res.result();
//            context.setUser(user);
//            if (session != null) {
//              String returnURL = session.remove(returnURLParam);
//              if (returnURL != null) {
//                // Now redirect back to the original url
//                doRedirect(req.response(), returnURL);
//                return;
//              }
//            }
//            // Either no session or no return url
//            if (directLoggedInOKURL != null) {
//              // Redirect to the default logged in OK page - this would occur
//              // if the user logged in directly at this URL without being redirected here first from another
//              // url
//              doRedirect(req.response(), directLoggedInOKURL);
//            } else {
//              // Just show a basic page
//              req.response().end(DEFAULT_DIRECT_LOGGED_IN_OK_PAGE);
//            }
//          } else {
//            context.fail(403);  // Failed login
//          }
//        });
//      }
  }
@Override
public FormLoginHandler setDirectLoggedInOKURL(String arg0) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public FormLoginHandler setPasswordParam(String arg0) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public FormLoginHandler setReturnURLParam(String arg0) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public FormLoginHandler setUsernameParam(String arg0) {
	// TODO Auto-generated method stub
	return null;
}

}