package api;

import anotations.Controller;
import anotations.GET;
import anotations.POST;
import anotations.Path;

@Controller
public class UsersController {

    @GET
    @Path(value = "/users")
    String getUsers(){
        return "/users";
    }

    @POST
    @Path(value = "/createUser")
    String createUser(){
        return "/createUser";
    }
}
