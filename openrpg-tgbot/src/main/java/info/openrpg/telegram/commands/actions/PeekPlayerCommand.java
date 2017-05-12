package info.openrpg.telegram.commands.actions;

import com.google.common.base.Joiner;
import info.openrpg.constants.Commands;
import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.telegram.commands.InlineCommand;
import info.openrpg.telegram.commands.MessageWrapper;
import info.openrpg.telegram.input.InputMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PeekPlayerCommand implements ExecutableCommand {
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();
    private static final String UNKNOWN_PLAYER_MESSAGE = "Ты попытался потыкать палкой несуществующего пидора.";
    private static final String PLAYER_PEEKED_MESSAGE = "Тебя потыкал палкой";

    private final PlayerRepository playerRepository;

    public PeekPlayerCommand(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return Optional.of(inputMessage)
                .filter(iM -> iM.hasArguments(1))
                .map(iM -> iM.getArgument(1))
                .map(userName -> getPlayerByUsername(inputMessage.getFrom(), userName, inputMessage.getChatId()))
                .map(MessageWrapper::new)
                .map(Collections::singletonList)
                .orElseGet(() -> playersButtonList(0, inputMessage.getChatId()));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }

    private SendMessage getPlayerByUsername(User from, String userName, Long chatId) {
        return playerRepository.findPlayerByUsername(userName)
                .map(player -> new SendMessage()
                        .setChatId(String.valueOf(player.getId()))
                        .setText(JOINER.join(PLAYER_PEEKED_MESSAGE, "@".concat(from.getUserName())))
                )
                .orElse(new SendMessage()
                        .setChatId(chatId)
                        .setText(UNKNOWN_PLAYER_MESSAGE)
                );
    }

    private List<MessageWrapper> playersButtonList(int offset, long chatId) {
        int playersNumber = playerRepository.selectPlayersNumber();
        List<Player> players = playerRepository.selectPlayerWithOffset(offset, 10);
        SendMessage sendMessage = new SendMessage()
                .setText("Список игроков:")
                .setReplyMarkup(InlineCommand.playerList(Commands.PEEK_PLAYER, players, offset, playersNumber))
                .setChatId(chatId);
        return Collections.singletonList(new MessageWrapper(sendMessage));
    }
}
