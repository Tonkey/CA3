package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import security.IUserFacade;
import security.UserFacadeFactory;

@Path("users")
@RolesAllowed("Admin")
public class Users {
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Object getUsers(){
      
      IUserFacade facade = UserFacadeFactory.getInstance();
      List<entity.User> users = facade.getAllUsers();
      return new Gson().toJson(users);
  }


}