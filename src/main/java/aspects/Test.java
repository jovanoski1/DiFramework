package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Test {

    @Before("execution(public static void server.Server.main(String[]))")
    public void x() throws Throwable{
        System.out.println("PRE");
        System.out.println("POSLE");
    }
}
