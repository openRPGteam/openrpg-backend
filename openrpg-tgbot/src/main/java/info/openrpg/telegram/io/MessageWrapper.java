package info.openrpg.telegram.io;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
public class MessageWrapper {
    private final SendMessage message;
    private final SendPhoto photo;

    public MessageWrapper(@NotNull SendMessage sendMessage) {
        message = sendMessage;
        photo = null;
    }

    public MessageWrapper(@NotNull SendPhoto sendPhoto) {
        message = null;
        photo = sendPhoto;
    }
}
