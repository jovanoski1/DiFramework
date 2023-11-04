package anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
    boolean verbose() default false;
    /** if verbose true sout
      “Initialized <param.object.type> <param.name>
          in <param.parentClass.name>
          on <localDateTime.now()>
          with <param.instance.hashCode>”
    */
}
