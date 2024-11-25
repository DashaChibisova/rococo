package guru.qa.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestMuseum {

    String city() default "";

    String country() default "";

    String title() default "";

    String description() default "";

    String photoPath() default "";

    int count() default 1;

    boolean fake() default false;

}
