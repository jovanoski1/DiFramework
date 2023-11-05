package api;

import anotations.*;

@Controller
public class UsersController {

    @Autowired
    @Qualified(value = "userA")
    User x;

    @Autowired
    UserA y;

    @GET
    @Path(value = "/x")
    public String getX(){
        return x.printUser();
    }

    @GET
    @Path(value = "/y")
    public String getY(){
        return y.printUser();
    }

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
