package info.openrpg.telegram;

import info.openrpg.database.models.Chat;
import info.openrpg.database.models.Message;
import info.openrpg.database.models.Player;
import info.openrpg.telegram.commands.CommandChooser;
import info.openrpg.telegram.commands.MessageWrapper;
import info.openrpg.telegram.commands.TelegramCommand;
import info.openrpg.telegram.commands.actions.ExecutableCommand;
import info.openrpg.telegram.input.InputMessage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class OpenRpgBot extends TelegramLongPollingBot {
    private static final Logger logger = Logger.getLogger("bot");
    private final CommandChooser commandChooser;

    private Credentials credentials;
    private SessionFactory sessionFactory;

    public OpenRpgBot(Credentials credentials, Properties properties) {
        this.credentials = credentials;
        this.commandChooser = new CommandChooser();
        this.sessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Chat.class)
                .buildSessionFactory();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional.of(update)
                .map(this::parseInputMessage)
                .flatMap(inputMessage -> inputMessage)
                .map(this::updateInputMessage)
                .map(this::logInputMessage)
                .ifPresent(this::parseCommand);
    }

    @Override
    public String getBotUsername() {
        return credentials.getBotName();
    }

    @Override
    public String getBotToken() {
        return credentials.getToken();
    }

    private InputMessage logInputMessage(InputMessage inputMessage) {
        logger.info(inputMessage.getChatId().toString());
        logger.info(inputMessage.getFrom().toString());
        logger.info(inputMessage.getText());
        return inputMessage;
    }

    private Optional<InputMessage> parseInputMessage(Update update) {
        return Optional.of(update)
                .map(Update::getMessage)
                .map(message -> new InputMessage(
                                message.getText(),
                                message.getChatId(),
                                message.getFrom(),
                                commandChooser
                        )
                )
                .map(Optional::of)
                .orElseGet(() -> parseCallback(update));
    }

    private InputMessage updateInputMessage(InputMessage inputMessage) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Optional<Message> messageQueue = entityManager.createQuery("from Message where player_id = :playerId", Message.class)
                .setParameter("playerId", inputMessage.getChatId())
                .getResultList()
                .stream()
                .findFirst();

        return messageQueue.map(message -> {
            entityManager.remove(message);
            entityManager.getTransaction().commit();
            entityManager.close();
            return message;
        })
                .map(message -> new InputMessage(inputMessage, message.getMessage(), commandChooser))
                .orElse(inputMessage);
    }

    private Optional<InputMessage> parseCallback(Update update) {
        return Optional.of(update)
                .map(Update::getCallbackQuery)
                .map(callbackQuery -> {
                    answerCallbackText(callbackQuery.getId());
                    return callbackQuery;
                })
                .map(callbackQuery ->
                        new InputMessage(
                                callbackQuery.getData(),
                                Long.valueOf(callbackQuery.getFrom().getId()),
                                callbackQuery.getFrom(),
                                commandChooser
                        )
                );
    }

    private void sendText(SendMessage sendMessage) {
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendImage(SendPhoto photo) {
        try {
            sendPhoto(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void answerCallbackText(String callbackQueryId) {
        try {
            answerCallbackQuery(new AnswerCallbackQuery().setCallbackQueryId(callbackQueryId).setShowAlert(false));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void parseCommand(InputMessage inputMessage) {
        Optional.of(inputMessage)
                .map(InputMessage::getCommand)
                .filter(command -> command != TelegramCommand.NOTHING)
                .ifPresent(telegramCommand -> executeCommand(telegramCommand, inputMessage));
    }

    private void executeCommand(TelegramCommand telegramCommand, InputMessage inputMessage) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ExecutableCommand command = telegramCommand.getExecutableCommand(entityManager);
        try {
            List<MessageWrapper> sendMessageList = command.execute(inputMessage);
            entityManager.getTransaction().commit();
            sendMessageList.forEach(this::sendMessageInWrapper);
        } catch (RuntimeException e) {
            handleCrash(e, entityManager, command, inputMessage);
        }
        entityManager.close();
    }

    private void sendMessageInWrapper(MessageWrapper messageWrapper) {
        messageWrapper.getMessage()
                .ifPresent(this::sendText);
        messageWrapper.getPhoto()
                .ifPresent(this::sendImage);
    }

    private void handleCrash(
            RuntimeException e,
            EntityManager entityManager,
            ExecutableCommand executableCommand,
            InputMessage inputMessage
    ) {
        entityManager.getTransaction().rollback();
        Optional.of(executableCommand.handleCrash(e, inputMessage))
                .filter(sendMessages -> !sendMessages.isEmpty())
                .orElseGet(() -> {
                    logger.warning(e.getClass().getName());
                    e.printStackTrace();
                    SendMessage sendMessage = new SendMessage()
                            .setText("Извини, что-то пошло не так.\nРазработчик получает пизды.")
                            .setChatId(inputMessage.getChatId());
                    MessageWrapper wrapper = new MessageWrapper(sendMessage);
                    return Collections.singletonList(wrapper);
                })
                .forEach(this::sendMessageInWrapper);
    }
}
