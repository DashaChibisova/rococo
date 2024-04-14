package guru.qa.rococo.jupiter.annotation;

import guru.qa.rococo.jupiter.extension.CreateArtistExtension;
import guru.qa.rococo.jupiter.extension.CreateUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//@ExtendWith(CreateArtistExtension.class)
public @interface Artist {

    String name() default "";

    String biography() default "";

    String photoPath() default "";

    int count() default 1;

    boolean fake() default false;
}
