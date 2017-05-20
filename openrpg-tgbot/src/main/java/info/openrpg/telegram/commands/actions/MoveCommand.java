package info.openrpg.telegram.commands.actions;

import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.gameserver.WorldInstance;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.image.processing.DTOMapper;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.image.processing.dto.ChunkDto;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command sends GET {@code /move/player-id/x/y} request to image server
 * Image server returns an image with moved player
 * After that the image will be sent as message back to user
 */
public class MoveCommand implements ExecutableCommand {
    private final PlayerDao playerDao;
    private final RequestSender requestSender;

    public MoveCommand(PlayerDao playerDao, RequestSender requestSender) {
        this.playerDao = playerDao;
        this.requestSender = requestSender;
    }

    /**
     * @param inputMessage  user io
     * @param worldInstance
     * @return list with singleton {@link MessageWrapper#MessageWrapper(SendPhoto)} or empty list
     * @throws IllegalStateException if backend server can't reach image server
     */
    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage, WorldInstance worldInstance) {
        return Optional.of(inputMessage)
                .filter(message -> message.hasArguments(1))
                .map(message -> {
                    worldInstance.movePlayerById(inputMessage.getFrom().getId(), MoveDirections.valueOf(message.getArgument(1)));
                    Chunk chunk = worldInstance.getChunkByPlayerId(inputMessage.getFrom().getId())
                            .orElseThrow(() -> new IllegalStateException("no chunk was found for player " + inputMessage.getFrom().getId()));
                    List<Player> players = worldInstance.getPlayersInSameChunkByPlayerId(inputMessage.getFrom().getId());

                    return playerDao.findPlayerByUsername(inputMessage.getFrom().getUserName())
                            .map(player -> movePlayer(inputMessage, chunk, players))
                            .orElseGet(Collections::emptyList);
                })
                .orElseGet(() -> Collections.singletonList(new MessageWrapper(Message.MOVE_BUTTONS.sendTo(inputMessage.getChatId()))));
    }

    /**
     * Method sends request to image server that moving player
     * Returned response of server is an link of image
     * Image will be downloaded via network and published to {@link MessageWrapper} as io stream of {@link SendPhoto}
     *
     * @return list with singleton {@link MessageWrapper#MessageWrapper(SendPhoto)} or empty list
     * @throws IllegalStateException if backend server can't reach image server
     */
    private List<MessageWrapper> movePlayer(InputMessage inputMessage, Chunk chunk, List<Player> players) {
        ChunkDto chunkDto = DTOMapper.createChunkDtoByPlayers(chunk, players);
        InputStream image = requestSender.createImage(chunkDto).orElseThrow(() -> new IllegalStateException("Can't reach image-server"));
        SendPhoto actualPhoto = new SendPhoto().setNewPhoto("image", image).setReplyMarkup(InlineButton.moveButtonList())
                .setChatId(inputMessage.getChatId());
        // TODO: вынести в отдельную команду
//        List<SendPhoto> receivers = players.stream()
//                .filter(player -> player.getPlayerId() != inputMessage.getChatId().intValue())
//                .map(player -> new SendPhoto().setChatId(player.getPlayerId().longValue()))
//                .map(sendPhoto -> sendPhoto.setReplyMarkup(InlineButton.moveButtonList()))
//                .map(SendPhoto::disableNotification)
//                .collect(Collectors.toList());
        return Collections.singletonList(new MessageWrapper(new MessageWrapper.PhotoToMultipleUsers(actualPhoto, Collections.emptyList())));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage, WorldInstance worldInstance) {
        return Collections.emptyList();
    }
}
