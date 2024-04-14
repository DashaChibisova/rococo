package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.jupiter.annotation.ApiLogin;
import guru.qa.rococo.jupiter.annotation.Artist;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.model.ArtistJson;
import guru.qa.rococo.jupiter.model.UserJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class CreateArtistExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace CREATE_ARTIST_NAMESPACE
            = ExtensionContext.Namespace.create(CreateArtistExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<Artist> artist = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Artist.class
        );
        if (artist.isPresent()) {
            Artist artistData = artist.get();
            List<ArtistJson> artistJsonList = new ArrayList<>();


            for (int i = 0; i < artistData.count(); i++) {
                artistJsonList.add(createArtist(artistData));
            }
            extensionContext.getStore(CREATE_ARTIST_NAMESPACE)
                    .put(extensionContext.getUniqueId(), artistJsonList);
        }
    }

    public abstract ArtistJson createArtist(Artist artist) throws IOException;
    public abstract void deleteArtist(UUID artistId);

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), Artist.class)
                .isPresent() &&
                (parameterContext.getParameter().getType().isAssignableFrom(ArtistJson.class)  ||
                parameterContext.getParameter().getType().isAssignableFrom(ArtistJson[].class));
    }

    @Override
    public ArtistJson[] resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        List<ArtistJson> artistJsons = extensionContext.getStore(CREATE_ARTIST_NAMESPACE).get(extensionContext.getUniqueId(), List.class);
        if (parameterContext.getParameter().getType().isAssignableFrom(ArtistJson[].class)) {
            return artistJsons.stream().toList().toArray(new ArtistJson[0]);
        } else {
            return new ArtistJson[]{artistJsons.get(0)};
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        List<ArtistJson> artistJsons = extensionContext.getStore(CREATE_ARTIST_NAMESPACE)
                .get(extensionContext.getUniqueId(), List.class);
        if(artistJsons != null) {
            for (ArtistJson artistJson : artistJsons) {
                deleteArtist(artistJson.id());
            }
        }
    }

}
