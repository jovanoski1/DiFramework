package api;

import anotations.Bean;
import anotations.Qualified;

@Bean
@Qualified(value = "userB")
public class UserB implements User{
    @Override
    public String printUser() {
        return "UserB";
    }
}
