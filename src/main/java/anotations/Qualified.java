package anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Qualified {
    String value();

    /**
     *Kada anotira klasu, predstavlja jedinstvenu vrednost po kojoj ćemo prepoznati ovu implementaciju.
     * Vrednost registrovati u Dependency Container-u.
     *
     *
     * Kada anotira atribut koji je interfejs, na tom mestu treba inject-ovati implementaciju (klasu)
     * registrovanu u Dependency Container-u sa navedenim Qualifier-om.
     * Iz ovoga sledi da svaki atribut čiji tip je neki interfejs i koji je anotiran sa @Autowired
     * mora imati i @Qualifier kako bi se navela određena implementacija.
     *
     */
}
