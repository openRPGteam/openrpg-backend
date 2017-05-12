package info.openrpg.telegram.commands.actions;

import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.telegram.commands.MessageWrapper;
import info.openrpg.telegram.input.InputMessage;
import org.hibernate.exception.ConstraintViolationException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.User;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;

public class StartCommand implements ExecutableCommand {

    private static final String ALREADY_REGISTERED_MESSAGE = "Ты уже зарегистрирован";
    private static final String FIRST_MESSAGE = "Спасибо за регистрацию";

    private final PlayerRepository playerRepository;

    public StartCommand(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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

        playerRepository.savePlayer(player);

        return Collections.singletonList(new MessageWrapper(new SendMessage()
                .setChatId(inputMessage.getChatId())
                .setText(FIRST_MESSAGE))
        );
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        if (e instanceof PersistenceException) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return Collections.singletonList(
                        new MessageWrapper(new SendMessage()
                                .setChatId(inputMessage.getChatId())
                                .setText(ALREADY_REGISTERED_MESSAGE)
                        )
                );
            }
        }
        return Collections.emptyList();
    }
}
