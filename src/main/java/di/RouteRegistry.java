package di;

import anotations.Path;
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

    public Object findControllerByMethodPath(String path){
        for(Map.Entry<String, Object> controller:controllerInstances.entrySet()){
            Method[] methods = controller.getValue().getClass().getDeclaredMethods();
            for (Method m : methods){
                if (m.isAnnotationPresent(Path.class) && m.getAnnotation(Path.class).value().equals(path)){
                    return controller.getValue();
                }
            }
        }
        return null;
    }
}
