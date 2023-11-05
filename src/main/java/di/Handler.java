package di;

import anotations.*;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
                System.out.println("Controller: " + clazz.getName() );
//                System.out.println("Methods: " + Arrays.toString(methods));
                for (Method m : methods){
                    String key = "";
                    if (!m.isAnnotationPresent(Path.class)) continue;
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


    public static void solveFields(Field f, Object classInstance) throws Throwable{
        DiEngine diEngine = DiEngine.getInstance();
        boolean isAccessible = f.isAccessible();
        f.setAccessible(true);

        if(f.isAnnotationPresent(Autowired.class)){
            Class<?> fieldType = f.getType();
            if (fieldType.isInterface()){
                Qualified qualified = f.getAnnotation(Qualified.class);
                if (f.isAnnotationPresent(Qualified.class)){
                    fieldType = diEngine.getDependencyContainter().get(qualified.value());
                }else {
                    System.out.println("Interface not @Qualified");
                }
            }

            try{
                if (diEngine.getBeanInstances().containsKey(fieldType.getName())) {
                    f.set(classInstance, diEngine.getBeanInstances().get(fieldType.getName()));
                }
                else f.set(classInstance, fieldType.getDeclaredConstructor().newInstance());

                if (f.getAnnotation(Autowired.class).verbose()){
                    System.out.println("Autowired: " + f.getName() + " verbose");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        f.setAccessible(isAccessible);
    }

    public static boolean isBean(Class<?> clazz){
        return clazz.isAnnotationPresent(Bean.class)
                || clazz.isAnnotationPresent(Service.class)
                || clazz.isAnnotationPresent(Component.class);
    }

    public static boolean isScopeSingleton(Class<?> clazz){
        if (clazz.getAnnotation(Bean.class) != null){
            System.out.println(clazz.getAnnotation(Bean.class).scope() + " prokic");
            return clazz.getAnnotation(Bean.class).scope().equals("singleton");
        }
        else if (clazz.getAnnotation(Service.class) != null){
            return clazz.getAnnotation(Service.class).scope().equals("singleton");
        }
        else if (clazz.getAnnotation(Component.class) != null){
            return clazz.getAnnotation(Component.class).scope().equals("singleton");
        }
        return false;
    }

    public static boolean containsAutowiredFields(Class<?> clazz){
        for (Field f : clazz.getDeclaredFields()){
            if (f.isAnnotationPresent(Autowired.class)){
                return true;
            }
        }
        return false;
    }
}
