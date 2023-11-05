package api;

import anotations.Autowired;
import anotations.Bean;
import anotations.Qualified;

@Bean(scope = "prototype")
@Qualified(value = "userA")
public class UserA implements User{
    @Autowired
    RandomInt randomInt;

    @Override
    public String printUser() {
        return "UserA " + randomInt.x;
    }
}
