package info.openrpg;

import info.openrpg.telegram.OpenRpgBot;
import info.openrpg.telegram.Credentials;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("main");

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
            Credentials botCredentials = new Credentials(
                    properties.getProperty("bot.name"),
                    properties.getProperty("bot.token"),
                    properties.getProperty("image-server.hostname"),
                    Integer.parseInt(properties.getProperty("image-server.port"))
            );
            telegramBotsApi.registerBot(new OpenRpgBot(botCredentials, properties));
        } catch (TelegramApiRequestException e) {
            logger.warning(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.warning("No file with properties was found");
        } catch (IOException e) {
            logger.warning("Exception while loading properties");
        } catch (NumberFormatException e) {
            logger.warning("Invalid port parameter of image-server");
        }
    }
}
