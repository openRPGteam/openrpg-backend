package info.openrpg.telegram.commands.actions;

import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.telegram.commands.MessageWrapper;
import info.openrpg.telegram.input.InputMessage;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MoveCommand implements ExecutableCommand {
    private final PlayerRepository playerRepository;

    public MoveCommand(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        if (inputMessage.hasArguments(2)) {
            int x = Integer.parseInt(inputMessage.getArgument(1));
            int y = Math.negateExact(Integer.parseInt(inputMessage.getArgument(2)));
            Optional<Player> playerByUsername = playerRepository.findPlayerByUsername(inputMessage.getFrom().getUserName());
            if (playerByUsername.isPresent()) {
                try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
                    CloseableHttpResponse get = client.execute(
                            new HttpHost("52.88.12.119", 8080),
                            new BasicHttpRequest(
                                    "GET",
                                    String.format("/move/%d/%d/%d", inputMessage.getFrom().getId(), x, y)
                            )
                    );
                    HttpEntity entity = get.getEntity();
                    String s = IOUtils.toString(entity.getContent(), Charset.defaultCharset());

                    SendPhoto sendPhoto = new SendPhoto()
                            .setNewPhoto(s, new URL(s).openConnection().getInputStream())
                            .setChatId(inputMessage.getChatId());
                    return Collections.singletonList(new MessageWrapper(sendPhoto));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }
}
