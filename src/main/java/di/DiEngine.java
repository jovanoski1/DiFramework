package di;

import anotations.Bean;
import anotations.Qualified;
import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DiEngine {

    private static DiEngine instance = null;
    private final Map<String, Class<?>> dependencyContainter = new HashMap<>();
    private final Map<String, Object> beanInstances = new HashMap<>();

    private DiEngine(){

    }

    public static DiEngine getInstance(){
        if (instance == null){
            instance = new DiEngine();
        }
        return instance;
    }

    public void registerQualified() throws Throwable{
        String basePackage = "api";

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(basePackage))
                .setScanners(new SubTypesScanner(false))
        );
        for (Class<?> clazz : reflections.getSubTypesOf(Object.class)) {
            Qualified qualified = clazz.getAnnotation(Qualified.class);
            Bean bean = clazz.getAnnotation(Bean.class);
            if (qualified != null && bean != null){
                dependencyContainter.put(qualified.value(), clazz);
            }
        }
    }

}
