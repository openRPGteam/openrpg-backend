package info.openrpg.telegram.commands.actions;

import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.gameserver.WorldInstance;
import info.openrpg.gameserver.enums.GameClass;
import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.actors.PlayerStats;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.gameserver.model.world.Location;
import info.openrpg.image.processing.DTOMapper;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.image.processing.dto.ChunkDto;
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
    public List<MessageWrapper> execute(InputMessage inputMessage, WorldInstance worldInstance) {
        info.openrpg.database.models.Player player = playerDao.findPlayerByUsername(inputMessage.getFrom().getUserName())
                .orElseThrow(IllegalStateException::new);

        worldInstance.addPlayer(
                new Player(
                        player.getUserName(),
                        new Location(4, 4, 5, 5),
                        new PlayerStats(-99, 99),
                        GameClass.ARCHER,
                        Race.HUMAN,
                        player.getId()));

        Chunk chunk = worldInstance.getChunkByPlayerId(inputMessage.getChatId().intValue())
                .orElseThrow(() -> new IllegalStateException("can't reach image server"));

        ChunkDto chunkDto = DTOMapper.ChunkDTO(chunk, 4, 4);

        return requestSender.createImage(chunkDto)
                .map(inputStream -> new SendPhoto().setNewPhoto("spawn", inputStream))
                .map(sendPhoto -> sendPhoto.setChatId(inputMessage.getChatId()))
                .map(sendPhoto -> sendPhoto.setReplyMarkup(InlineButton.moveButtonList()))
                .map(MessageWrapper::new)
                .map(Collections::singletonList)
                .orElseThrow(() -> new IllegalStateException("can't reach image server"));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage, WorldInstance worldInstance) {
        if (e instanceof IllegalStateException) {
            return Collections.singletonList(new MessageWrapper(
                    Message.CANT_CONNECT.sendTo(inputMessage.getChatId())
            ));
        }
        return Collections.emptyList();
    }
}
