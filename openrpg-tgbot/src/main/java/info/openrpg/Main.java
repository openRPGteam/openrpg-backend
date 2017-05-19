package info.openrpg;

import info.openrpg.configuration.PropertiesConfiguration;
import info.openrpg.telegram.Credentials;
import info.openrpg.telegram.OpenRpgBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.Properties;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("main");

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Properties botProperties = PropertiesConfiguration.getApplicationProperties();
        try {
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
        }
    }
}
