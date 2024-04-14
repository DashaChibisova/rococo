package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.MuseumEntity;
import guru.qa.rococo.jupiter.annotation.Artist;
import guru.qa.rococo.jupiter.annotation.TestMuseum;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class CreateMuseumExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace CREATE_MUSEUM_NAMESPACE
            = ExtensionContext.Namespace.create(CreateMuseumExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<TestMuseum> museum = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                TestMuseum.class
        );
        if (museum.isPresent()) {
            TestMuseum museumData = museum.get();
            List<MuseumJson> museumJsonList = new ArrayList<>();


            for (int i = 0; i < museumData.count(); i++) {
                museumJsonList.add(createMuseum(museumData));
            }
            extensionContext.getStore(CREATE_MUSEUM_NAMESPACE)
                    .put(extensionContext.getUniqueId(), museumJsonList);
        }
    }

    public abstract MuseumJson createMuseum(TestMuseum museum) throws IOException;
    public abstract void deleteMuseum(UUID museumId);

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), TestMuseum.class)
                .isPresent() &&
                (parameterContext.getParameter().getType().isAssignableFrom(MuseumJson.class)  ||
                parameterContext.getParameter().getType().isAssignableFrom(MuseumJson[].class));
    }

    @Override
    public MuseumJson[] resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        List<MuseumJson> museumJsons = extensionContext.getStore(CREATE_MUSEUM_NAMESPACE).get(extensionContext.getUniqueId(), List.class);
        if (parameterContext.getParameter().getType().isAssignableFrom(MuseumJson[].class)) {
            return museumJsons.stream().toList().toArray(new MuseumJson[0]);
        } else {
            return new MuseumJson[]{museumJsons.get(0)};
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        List<MuseumJson> museumJsons = extensionContext.getStore(CREATE_MUSEUM_NAMESPACE)
                .get(extensionContext.getUniqueId(), List.class);
        if(museumJsons != null) {
        for (MuseumJson museumJson : museumJsons) {
            deleteMuseum(museumJson.id());
        }
        }
    }

}
