package info.openrpg.telegram.commands.actions;

import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;
import org.hibernate.exception.ConstraintViolationException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.User;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartCommand implements ExecutableCommand {
    private static final String FIRST_MESSAGE = "Спасибо за регистрацию";

    private final PlayerDao playerDao;
    private final RequestSender requestSender;

    public StartCommand(
            PlayerDao playerDao,
            RequestSender requestSender
    ) {
        this.playerDao = playerDao;
        this.requestSender = requestSender;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        User user = inputMessage.getFrom();
        Player player = Player.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .build();

        playerDao.savePlayer(player);


        List<MessageWrapper> messageWrappers = new ArrayList<>();
        messageWrappers.add(new MessageWrapper(new SendMessage()
                        .setChatId(inputMessage.getChatId())
                        .setText(FIRST_MESSAGE)));
        messageWrappers.addAll(new SpawnCommand(playerDao, requestSender).execute(inputMessage));

        return messageWrappers;
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        if (e instanceof PersistenceException) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return new SpawnCommand(playerDao, requestSender).execute(inputMessage);
            }
        }
        return Collections.emptyList();
    }
}
