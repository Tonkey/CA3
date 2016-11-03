package rest;

import com.google.gson.Gson;
import entity.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.IUserFacade;
import security.PasswordStorage;
import security.UserFacadeFactory;

@Path("/users")
@RolesAllowed("Admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users {

    @GET
    public Response getUsers() throws PasswordStorage.CannotPerformOperationException {
        IUserFacade facade = UserFacadeFactory.getInstance();
        
        List<User> users = facade.getAllUsers();

        String json = new Gson().toJson(users);

        return Response.ok(json).build();
    }

    @DELETE
    public Response deleteUser(String data) {
        IUserFacade facade = UserFacadeFactory.getInstance();
        
        User json = new Gson().fromJson(data, User.class);
        
        facade.deleteUser(json.getUserName());

        return Response.ok().build();
    }
}
