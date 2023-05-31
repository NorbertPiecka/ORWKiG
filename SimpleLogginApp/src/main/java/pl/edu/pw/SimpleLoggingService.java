package pl.edu.pw;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class SimpleLoggingService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleLoggingService.class);

    public static void main(String[] args) {
        String state;
        do {
            int wordLength = RandomUtils.nextInt(3, 12);
            byte[] array = new byte[wordLength];
            new Random().nextBytes(array);
            String generatedString = new String(array, StandardCharsets.US_ASCII);
            LoggingLevel level;
            switch (wordLength) {
                case 3, 4, 5 -> {
                    logger.warn(generatedString);
                    level = LoggingLevel.WARN;
                }
                case 6 -> {
                    logger.error(generatedString);
                    level = LoggingLevel.ERROR;
                }
                default -> {
                    logger.info(generatedString);
                    level = LoggingLevel.INFO;
                }
            }

            System.out.printf("Generated word: %s%nLogging level: %s%n", generatedString, level);

            Scanner scanner = new Scanner(System.in);
            state = scanner.nextLine();
        } while (!Objects.equals(state, "stop"));
    }

    private enum LoggingLevel {
        INFO, WARN, ERROR;
    }
}