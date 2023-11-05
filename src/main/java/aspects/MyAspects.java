package aspects;

import di.DiEngine;
import di.Handler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.reflect.Field;

@Aspect
public class MyAspects {

    @Around("execution(*.new(..)) && ( within(server.*) || within(framework.*) || within(api.*))")
    public Object constructorAspect(ProceedingJoinPoint jp) throws Throwable{
        DiEngine diEngine = DiEngine.getInstance();
        jp.proceed();

        Object classInstance = jp.getTarget();
        System.out.println(classInstance.getClass().getName());
        Class<?> clazz = classInstance.getClass();

        /// ako je bean i ako je singleton i ako je vec instanciran
        if (Handler.isBean(clazz)){
            if (Handler.isScopeSingleton(clazz)){
                Object instance = diEngine.getBeanInstances().get(clazz.getName());
                if (instance != null){
                    return instance;
                }
            }
        }

        /// ako je bean i ako je singleton i ako nije instanciran i nema @Autowired polja
        if (!Handler.containsAutowiredFields(clazz) && Handler.isBean(clazz) && Handler.isScopeSingleton(clazz)){
            diEngine.getBeanInstances().put(clazz.getName(), classInstance);
        }
        else{
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields){
                System.out.println(f.getName());
                Handler.solveFields(f, classInstance);
            }

            if (Handler.isBean(clazz) && Handler.isScopeSingleton(clazz)){
                diEngine.getBeanInstances().put(clazz.getName(), classInstance);
            }
        }
        System.out.println("-------------------------");
        return classInstance;
    }

}
