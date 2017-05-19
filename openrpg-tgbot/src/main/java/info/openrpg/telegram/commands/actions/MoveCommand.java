package info.openrpg.telegram.commands.actions;

import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.gameserver.WorldInstance;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * @param inputMessage user io
     * @param worldInstance
     * @return list with singleton {@link MessageWrapper#MessageWrapper(SendPhoto)} or empty list
     * @throws IllegalStateException if backend server can't reach image server
     */
    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage, WorldInstance worldInstance) {
        return Optional.of(inputMessage)
                .filter(message -> message.hasArguments(2))
                .map(message -> {
//                    worldInstance.movePlayer(inputMessage.getChatId().intValue(), );
                    int x = Integer.parseInt(inputMessage.getArgument(1));
                    int y = Integer.parseInt(inputMessage.getArgument(2));
                    return playerDao.findPlayerByUsername(inputMessage.getFrom().getUserName())
                            .map(player -> sendMoveCommandToImageServer(inputMessage, x, y))
                            .orElseGet(Collections::emptyList);
                })
                .orElseGet(() -> Collections.singletonList(new MessageWrapper(Message.MOVE_BUTTONS.sendTo(inputMessage.getChatId()))));
    }

    /**
     * Method sends request to image server that moving player
     * Returned response of server is an link of image
     * Image will be downloaded via network and published to {@link MessageWrapper} as io stream of {@link SendPhoto}
     * @param inputMessage user io
     * @param x x value of vector to move
     * @param y y value of vector to move
     * @return list with singleton {@link MessageWrapper#MessageWrapper(SendPhoto)} or empty list
     * @throws IllegalStateException if backend server can't reach image server
     */
    private List<MessageWrapper> sendMoveCommandToImageServer(InputMessage inputMessage, int x, int y) {
            SendPhoto sendPhoto = new SendPhoto()
                    .setNewPhoto("image", requestSender.sendMove(inputMessage.getChatId(), x, y)
                            .orElseThrow(() -> new IllegalStateException("Can't reach image-server")))
                    .setChatId(inputMessage.getChatId())
                    .setReplyMarkup(InlineButton.moveButtonList());
            MessageWrapper messageWrapper = new MessageWrapper(sendPhoto);
            return Collections.singletonList(messageWrapper);
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage, WorldInstance worldInstance) {
        return Collections.emptyList();
    }
}
