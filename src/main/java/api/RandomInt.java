package api;

import anotations.Bean;

@Bean(scope = "prototype")
public class RandomInt {
    int x;
    public RandomInt() {
        x = (int) (Math.random() * 100);
    }

}
