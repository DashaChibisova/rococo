package guru.qa.rococo.jupiter.annotation.meta;

import guru.qa.rococo.jupiter.extension.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({ContextHolderExtension.class, AllureJunit5.class, BrowserExtension.class, HibernateCreteUserExtension.class, ApiLoginExtension.class, HibernateCreateArtistExtension.class, HibernateCreateMuseumExtension.class, HibernateCreatePaintingExtension.class})
public @interface WebTest {
}
