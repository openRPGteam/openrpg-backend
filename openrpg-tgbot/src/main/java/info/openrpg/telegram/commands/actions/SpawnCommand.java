package info.openrpg.telegram.commands.actions;

import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpawnCommand implements ExecutableCommand {
    private final PlayerRepository playerRepository;
    private final RequestSender requestSender;

    public SpawnCommand(PlayerRepository playerRepository, RequestSender requestSender) {
        this.playerRepository = playerRepository;
        this.requestSender = requestSender;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return playerRepository.findPlayerByUsername(inputMessage.getFrom().getUserName())
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
        return Collections.emptyList();
    }
}
