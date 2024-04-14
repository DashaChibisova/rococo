package guru.qa.rococo.jupiter.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestPainting {

    String title() default "";

    String description() default "";

    String content() default "";

    TestMuseum museum() default @TestMuseum(fake = true);

    Artist artist() default @Artist(fake = true);

    int count() default 1;
}
