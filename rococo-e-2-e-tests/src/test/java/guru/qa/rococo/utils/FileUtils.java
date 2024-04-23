package guru.qa.rococo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtils {

    public static byte[] encodedFileBytes(String path) throws IOException {
        File fi = new File(path);
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        String encodedFile = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
        return encodedFile.getBytes(StandardCharsets.UTF_8);
    }
}
