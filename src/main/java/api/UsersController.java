package api;

import anotations.*;

@Controller
public class UsersController {

    @Autowired
    @Qualified(value = "userA")
    User x;

    @Autowired
    UserA y;

    @Autowired
    UserB z;
    @Autowired
    UserB z2;

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
    @Path(value = "/z")
    public String getZ(){
        return z.printUser();
    }

    @GET
    @Path(value = "/z2")
    public String getZ2(){
        return z2.printUser() + " z2";
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
