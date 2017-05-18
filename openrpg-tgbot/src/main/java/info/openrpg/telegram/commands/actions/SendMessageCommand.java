package info.openrpg.telegram.commands.actions;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import info.openrpg.database.models.Message;
import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.MessageDao;
import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.InputMessage;
import info.openrpg.telegram.io.MessageWrapper;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SendMessageCommand implements ExecutableCommand {

    private static final Joiner JOINER = Joiner.on(" ").skipNulls();
    private static final String UNKNOWN_PLAYER_MESSAGE = "Ты попытался потыкать палкой несуществующего пидора.";
    private static final String PLAYER_PEEKED_MESSAGE = "Тебе передал сообщение";

    private final PlayerDao playerDao;
    private final MessageDao messageDao;

    @Inject
    public SendMessageCommand(PlayerDao playerDao, MessageDao messageDao) {
        this.playerDao = playerDao;
        this.messageDao = messageDao;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return Optional.of(inputMessage)
                .filter(iM -> iM.hasArguments(2))
                .map(iM -> iM.getArgument(1))
                .map(username -> playerDao.findPlayerByUsername(username)
                        .map(player -> new SendMessage()
                                .setChatId(new Long(player.getId()))
                                .setText(JOINER.join(
                                        PLAYER_PEEKED_MESSAGE,
                                        "@".concat(inputMessage.getFrom().getUserName()).concat(":"),
                                        JOINER.join(
                                                IntStream.rangeClosed(2, inputMessage.size())
                                                        .mapToObj(inputMessage::getArgument)
                                                        .collect(Collectors.toList()))
                                        )
                                )
                        )
                        .map(MessageWrapper::new)
                        .map(Collections::singletonList)
                        .orElse(Collections.singletonList(
                                new MessageWrapper(new SendMessage()
                                        .setChatId(inputMessage.getChatId())
                                        .setText(UNKNOWN_PLAYER_MESSAGE))
                                )
                        )
                )
                .orElseGet(() -> parseArguments(inputMessage));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }

    private List<MessageWrapper> parseArguments(InputMessage inputMessage) {
        return Optional.of(inputMessage)
                .filter(iM -> iM.hasArguments(1))
                .map(this::typeSendMessage)
                .orElseGet(() -> playersButtonList(0, inputMessage.getChatId()));
    }

    private List<MessageWrapper> typeSendMessage(InputMessage inputMessage) {
        Player player = playerDao.findPlayerByUsername(inputMessage.getFrom().getUserName())
                .orElseThrow(() -> new RuntimeException("WTF"));
        Message message = Message.builder()
                .player(player)
                .message(inputMessage.getText())
                .build();
        messageDao.saveMessage(message);
        return Collections.singletonList(new MessageWrapper(new SendMessage()
                .setText("Напишите текст, который вы хотите отправить:")
                .setChatId(inputMessage.getChatId())));
    }

    private List<MessageWrapper> playersButtonList(int offset, long chatId) {
        int playersNumber = playerDao.selectPlayersNumber();
        List<Player> players = playerDao.selectPlayerWithOffset(offset, 10);
        SendMessage sendMessage = new SendMessage()
                .setText("Выберите игрока, которому вы хотите отправить сообщение:")
                .setReplyMarkup(InlineButton.playerList(Command.SEND_MESSAGE, players, offset, playersNumber))
                .setChatId(chatId);
        return Collections.singletonList(new MessageWrapper(sendMessage));
    }
}
