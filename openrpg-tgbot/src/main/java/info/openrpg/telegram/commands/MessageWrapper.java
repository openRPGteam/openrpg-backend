package info.openrpg.telegram.commands;

import lombok.Getter;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.util.Optional;

@Getter
public class MessageWrapper {
    private final Optional<SendMessage> message;
    private final Optional<SendPhoto> photo;

    public MessageWrapper(SendMessage sendMessage) {
        message = Optional.of(sendMessage);
        photo = Optional.empty();
    }

    public MessageWrapper(SendPhoto sendPhoto) {
        message = Optional.empty();
        photo = Optional.of(sendPhoto);
    }
}
