package guru.qa.rococo.jupiter.annotation;

import guru.qa.rococo.jupiter.extension.CreateUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(CreateUserExtension.class)
public @interface DbUser {
    boolean fake() default false;

    String username() default "";

    String password() default "";
}
