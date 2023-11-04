package api;

import anotations.Bean;
import anotations.Qualified;

@Bean(scope = "prototype")
@Qualified(value = "userA")
public class UserA implements User{
    @Override
    public String printUser() {
        return "UserA";
    }
}
