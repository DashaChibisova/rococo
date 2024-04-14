package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.jupiter.annotation.TestMuseum;
import guru.qa.rococo.jupiter.annotation.TestPainting;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.MuseumJson;
import guru.qa.rococo.jupiter.model.PaintingJson;
import guru.qa.rococo.jupiter.model.UserJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.io.IOException;
import java.util.*;

public abstract class CreatePaintingExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace CREATE_PAINTING_NAMESPACE
            = ExtensionContext.Namespace.create(CreatePaintingExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<TestPainting> painting = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                TestPainting.class
        );
        if (painting.isPresent()) {
            TestPainting paintingData = painting.get();
            List<PaintingJson> paintingJsonList = new ArrayList<>();
//            Map<PaintingJson, List<UserJson>> createdUsers = new HashMap<>();

            for (int i = 0; i < paintingData.count(); i++) {

                paintingJsonList.add(createPainting(paintingData));
//                paintingJsonList.add(getCreatedArtist(extensionContext, ));
//                paintingJsonList.add(createArtist(paintingData));
            }
            extensionContext.getStore(CREATE_PAINTING_NAMESPACE)
                    .put(extensionContext.getUniqueId(), paintingJsonList);
        }
    }

//    private static ArtistJson getCreatedArtist(ExtensionContext extensionContext) {
//        return extensionContext.getStore(CreateArtistExtension.CREATE_ARTIST_NAMESPACE).get(extensionContext.getUniqueId(), ArtistJson.class);
//    }
//
//    private static MuseumJson getCreatedMuseum(ExtensionContext extensionContext) {
//        return extensionContext.getStore(CreateMuseumExtension.CREATE_MUSEUM_NAMESPACE).get(extensionContext.getUniqueId(), MuseumJson.class);
//    }


    public abstract PaintingJson createPainting(TestPainting painting) throws IOException;
    public abstract void deletePainting(PaintingJson painting);

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), TestPainting.class)
                .isPresent() &&
                (parameterContext.getParameter().getType().isAssignableFrom(PaintingJson.class)  ||
                parameterContext.getParameter().getType().isAssignableFrom(PaintingJson[].class));
    }

    @Override
    public PaintingJson[] resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        List<PaintingJson> paintingJsons = extensionContext.getStore(CREATE_PAINTING_NAMESPACE).get(extensionContext.getUniqueId(), List.class);
        if (parameterContext.getParameter().getType().isAssignableFrom(MuseumJson[].class)) {
            return paintingJsons.stream().toList().toArray(new PaintingJson[0]);
        } else {
            return new PaintingJson[]{paintingJsons.get(0)};
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        List<PaintingJson> paintingJsons = extensionContext.getStore(CREATE_PAINTING_NAMESPACE)
                .get(extensionContext.getUniqueId(), List.class);
        if(paintingJsons != null) {
            for (PaintingJson paintingJson : paintingJsons) {
                deletePainting(paintingJson);
            }
        }
    }

}
