package api;

import anotations.*;

@Controller
public class UsersController {

    @Autowired
    UserA x;

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
