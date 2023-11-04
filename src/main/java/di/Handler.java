package di;

import anotations.*;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Handler {

    public static void registerRoutes() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String basePackage = "api";

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(basePackage))
                .setScanners(new SubTypesScanner(false))
        );

        for (Class<?> clazz : reflections.getSubTypesOf(Object.class)) {
            Controller cont = clazz.getAnnotation(Controller.class);
            if (cont != null){
                Constructor<?> c = clazz.getDeclaredConstructor();
                RouteRegistry.getInstance().getControllerInstances().put(clazz.getName(), c.newInstance());
                Method[] methods = clazz.getDeclaredMethods();
                for (Method m : methods){
                    String key = "";
                    if (m.isAnnotationPresent(GET.class)){
                        key += "GET";
                    }
                    else key+="POST";

                    key+=m.getAnnotation(Path.class).value();
                    System.out.println(key + ": "+  m.getName());
                    RouteRegistry.getInstance().getRouteHandlers().put(key, m);
                }
            }
        }
    }

    public static void registerQualified() throws Throwable{
        String basePackage = "api";

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(basePackage))
                .setScanners(new SubTypesScanner(false))
        );
        for (Class<?> clazz : reflections.getSubTypesOf(Object.class)) {
            Qualified qualified = clazz.getAnnotation(Qualified.class);
            Bean bean = clazz.getAnnotation(Bean.class);
            if (qualified != null && bean != null){
                DiEngine.getInstance().getDc().put(qualified.value(), clazz);
            }
        }
    }
}
