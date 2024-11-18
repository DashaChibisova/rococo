package guru.qa.rococo.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtils {

    public static byte[] encodedFileBytes(String path) {
        String imageAsBase64 = null;

        try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(path)) {
            imageAsBase64 = new String(Base64.getEncoder().encode(is.readAllBytes()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String encodedFile = "data:image/png;base64," + imageAsBase64;
        return encodedFile.getBytes(StandardCharsets.UTF_8);
    }
}
