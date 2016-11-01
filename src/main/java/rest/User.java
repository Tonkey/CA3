package rest;

import com.google.gson.Gson;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.IUserFacade;
import security.UserFacadeFactory;

@Path("demouser")
@RolesAllowed("User")
public class User {
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSomething(){
    return "{\"message\" : \"REST call accesible by only authenticated USERS\"}"; 
  }
 
  
  
  
}