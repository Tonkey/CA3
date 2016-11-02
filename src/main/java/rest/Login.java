package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nimbusds.jose.*;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import entity.User;
import security.Authenticator;
import security.IUserFacade;
import security.UserFacadeFactory;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Login {

    /**
     *
     * @param jsonString
     * @return
     * @throws JOSEException
     */
    @POST
    public Response login(String jsonString) throws JOSEException {

        if (jsonString != null && !jsonString.isEmpty()) {
            try {
                JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                String username = json.get("username").getAsString();
                String password = json.get("password").getAsString();
                JsonObject responseJson = new JsonObject();
                List<String> roles;

                if ((roles = Authenticator.authenticate(username, password)) != null) {

                    System.out.println("2");

                    String token = Authenticator.createToken(username, roles);
                    responseJson.addProperty("username", username);
                    responseJson.addProperty("token", token);
                    return Response.ok(new Gson().toJson(responseJson)).build();
                }
            } catch (JOSEException | JsonSyntaxException e) {

                if (e instanceof JsonSyntaxException) {
                    return Response.status(400).build();
                }

                if (e instanceof JOSEException) {
                    return Response.status(400).build();
                    //throw e;
                }
            }
        }
        throw new NotAuthorizedException("Invalid username or password. Please try again", Response.Status.UNAUTHORIZED);
    }

    /**
     *
     * @param data
     * @return
     */
    @POST
    @Path("createUser")
    public Response createUser(String data) {

        IUserFacade facade = UserFacadeFactory.getInstance();

        //password not hashed here as it uses empty constructor!
        User u = new Gson().fromJson(data, User.class);

        facade.createNewUser(u);

        return Response.status(201).header("Location", "#/view1").build();
    }

}
