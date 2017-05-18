package info.openrpg.telegram.commands.actions;

import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.InputMessage;
import info.openrpg.telegram.io.MessageWrapper;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.util.Collections;
import java.util.List;

public class SpawnCommand implements ExecutableCommand {
    private final PlayerDao playerDao;
    private final RequestSender requestSender;

    public SpawnCommand(PlayerDao playerDao, RequestSender requestSender) {
        this.playerDao = playerDao;
        this.requestSender = requestSender;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return playerDao.findPlayerByUsername(inputMessage.getFrom().getUserName())
                .flatMap(player -> requestSender.spawnPlayer(inputMessage.getChatId()))
                .map(inputStream -> new SendPhoto().setNewPhoto("spawn", inputStream))
                .map(sendPhoto -> sendPhoto.setChatId(inputMessage.getChatId()))
                .map(sendPhoto -> sendPhoto.setReplyMarkup(InlineButton.moveButtonList()))
                .map(MessageWrapper::new)
                .map(Collections::singletonList)
                .orElseThrow(() -> new IllegalStateException("can't reach image server"));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        if (e instanceof IllegalStateException) {
            return Collections.singletonList(new MessageWrapper(
                    Message.CANT_CONNECT.sendTo(inputMessage.getChatId())
            ));
        }
        return Collections.emptyList();
    }
}
