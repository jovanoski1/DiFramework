package api;

import anotations.Autowired;
import anotations.Bean;
import anotations.Qualified;
import anotations.Service;

@Service
@Qualified(value = "userB")
public class UserB implements User{

    @Autowired
    RandomInt randomInt;
    @Override
    public String printUser() {
        return "UserB " + randomInt.x;
    }
}
