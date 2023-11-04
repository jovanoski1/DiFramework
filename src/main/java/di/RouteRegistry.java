package di;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Getter
public class RouteRegistry {

    private static RouteRegistry instance = null;
    private final Map<String, Method> routeHandlers = new HashMap<>();
    private final Map<String, Object> controllerInstances = new HashMap<>();

    private RouteRegistry(){

    }

    public static RouteRegistry getInstance(){
        if (instance == null){
            instance = new RouteRegistry();
        }
        return instance;
    }
}
