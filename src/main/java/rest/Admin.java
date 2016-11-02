package rest;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import security.IUserFacade;
import security.UserFacadeFactory;

@Path("demoadmin")
//@RolesAllowed("Admin")
public class Admin {
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("check")
  public String getSomething(){
    String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
    return "{\"message\" : \"REST call accesible by only authenticated ADMINS\",\n"+"\"serverTime\": \""+now +"\"}"; 
  }
 
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("getUsers")
  public Object getUsers(){
      IUserFacade facade = UserFacadeFactory.getInstance();
      
      
      List<entity.User> users = facade.getAllUsers();
      return new Gson().toJson(users);
  }
// 
//  @DELETE
//  @Consumes(MediaType.APPLICATION_JSON)
//  @Produces(MediaType.APPLICATION_JSON)
//  @Path("functioasn")
//  public Response deleteUser(String data){
//      IUserFacade facade = UserFacadeFactory.getInstance();
//      
//      facade.deleteUser(data);
//      
//      return Response.status(201).header("Location", "#/view5").build();
//  }
//  
}
