package info.openrpg.telegram.commands.actions;

import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Command sends GET {@code /move/player-id/x/y} request to image server
 * Image server returns an image with moved player
 * After that the image will be sent as message back to user
 */
public class MoveCommand implements ExecutableCommand {
    private final PlayerRepository playerRepository;

    public MoveCommand(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * @param inputMessage user io
     * @return list with singleton {@link MessageWrapper#MessageWrapper(SendPhoto)} or empty list
     * @throws InvalidStateException if backend server can't reach image server
     */
    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return Optional.of(inputMessage)
                .filter(message -> message.hasArguments(2))
                .map(message -> {
                    int x = Integer.parseInt(inputMessage.getArgument(1));
                    int y = Math.negateExact(Integer.parseInt(inputMessage.getArgument(2)));
                    return playerRepository.findPlayerByUsername(inputMessage.getFrom().getUserName())
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
     * @throws InvalidStateException if backend server can't reach image server
     */
    private List<MessageWrapper> sendMoveCommandToImageServer(InputMessage inputMessage, int x, int y) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            CloseableHttpResponse get = client.execute(
                    new HttpHost("localhost", 8080),
                    new BasicHttpRequest(
                            "GET",
                            String.format("/move/%d/%d/%d", inputMessage.getFrom().getId(), x, y)
                    )
            );
            String response = IOUtils.toString(get.getEntity().getContent(), Charset.defaultCharset());
            SendPhoto sendPhoto = new SendPhoto()
                    .setNewPhoto(response, new URL(response).openConnection().getInputStream())
                    .setChatId(inputMessage.getChatId())
                    .setReplyMarkup(InlineButton.moveButtonList());
            MessageWrapper messageWrapper = new MessageWrapper(sendPhoto);
            return Collections.singletonList(messageWrapper);
        } catch (IOException e) {
            throw new InvalidStateException("Can't reach image-server");
        }
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }
}
