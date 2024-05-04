package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.EmfProvider;
import jakarta.persistence.EntityManagerFactory;

public class EmfExtension implements SuiteExtension {

    @Override
    public void afterSuite() {
        EmfProvider.INSTANCE.storedEmf().forEach(
                EntityManagerFactory::close
        );
    }
}
