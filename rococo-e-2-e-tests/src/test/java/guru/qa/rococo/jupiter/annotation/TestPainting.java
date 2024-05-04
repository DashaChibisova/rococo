package guru.qa.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestPainting {

    String title() default "";

    String description() default "";

    String content() default "";

    TestMuseum museum() default @TestMuseum(fake = true);

    TestArtist artist() default @TestArtist(fake = true);

    int count() default 1;
}
