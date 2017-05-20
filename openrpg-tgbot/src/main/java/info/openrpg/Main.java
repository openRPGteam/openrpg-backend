package info.openrpg;

import info.openrpg.configuration.PropertiesConfiguration;
import info.openrpg.telegram.Credentials;
import info.openrpg.telegram.OpenRpgBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("main");

    // optional
    // -Dproperties=path/to/file -Ddatabase=path/to/file -Dhibernate=path/to/file
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            PropertiesConfiguration.initFromArgs(getParsedArguments(args));
            Properties botProperties = PropertiesConfiguration.getApplicationProperties();
            Credentials botCredentials = new Credentials(
                    botProperties.getProperty("bot.name"),
                    botProperties.getProperty("bot.token"),
                    botProperties.getProperty("image-server.hostname"),
                    Integer.parseInt(botProperties.getProperty("image-server.port"))
            );
            telegramBotsApi.registerBot(new OpenRpgBot(botCredentials));
        } catch (TelegramApiRequestException e) {
            logger.warning(e.getMessage());
        } catch (NumberFormatException e) {
            logger.warning("Invalid port parameter of image-server");
        } catch (IOException e) {
            logger.warning("Provided files in -args cannot be accessed: " + e.getMessage());
        }
    }

    public static Map<String, String> getParsedArguments(String[] args) {
        if (args.length < 1)
            return null;
        Map<String, String> parsedArgs = new HashMap<>();
        String[] tokens;
        for (String s : args) {
            tokens = s.split("=");
            try {
                parsedArgs.put(tokens[0], tokens[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("There must be no spaces in paths to files (ie ../my folder/..)");
            }
        }
        return parsedArgs;
    }
}
