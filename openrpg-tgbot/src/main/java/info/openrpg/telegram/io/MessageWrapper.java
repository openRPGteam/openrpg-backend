package info.openrpg.telegram.io;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
public class MessageWrapper {
    private final Optional<SendMessage> message;
    private final Optional<SendPhoto> photo;

    public MessageWrapper(@NotNull SendMessage sendMessage) {
        message = Optional.of(sendMessage);
        photo = Optional.empty();
    }

    public MessageWrapper(@NotNull SendPhoto sendPhoto) {
        message = Optional.empty();
        photo = Optional.of(sendPhoto);
    }

    public MessageWrapper(@NotNull SendMessage sendMessage, @NotNull SendPhoto sendPhoto) {
        message = Optional.of(sendMessage);
        photo = Optional.of(sendPhoto);
    }
}
