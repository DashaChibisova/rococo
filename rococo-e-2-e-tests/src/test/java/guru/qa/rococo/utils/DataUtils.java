package guru.qa.rococo.utils;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Nonnull;

public class DataUtils {

    private static final Faker faker = new Faker();

    @Nonnull
    public static String generateRandomUsername() {
        return faker.name().username();
    }

    @Nonnull
    public static String generateRandomPassword() {
        return faker.bothify("????####");
    }

    @Nonnull
    public static String generateRandomName() {
        return faker.name().firstName();
    }

    @Nonnull
    public static String generateRandomSurname() {
        return faker.name().lastName();
    }

    @Nonnull
    public static String generateRandomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }

    @Nonnull
    public static String generateRandomCity() {
        return faker.address().cityName();
    }

    @Nonnull
    public static String generateRandomCountry() {
        return faker.address().country();
    }

    @Nonnull
    public static String generateRandomString(int charCount) {
        return RandomStringUtils.randomAlphabetic(charCount);
    }
}
