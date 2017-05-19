package info.openrpg.telegram.io;

import lombok.Getter;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class MessageWrapper {
    private final SendMessage message;
    private final SendPhoto photo;
    private final PhotoToMultipleUsers photoToMultipleUsers;

    public MessageWrapper(@NotNull SendMessage sendMessage) {
        message = sendMessage;
        photo = null;
        photoToMultipleUsers = null;
    }

    public MessageWrapper(@NotNull SendPhoto sendPhoto) {
        message = null;
        photo = sendPhoto;
        photoToMultipleUsers = null;
    }

    public MessageWrapper(@NotNull PhotoToMultipleUsers photoToMultipleUsers) {
        message = null;
        photo = null;
        this.photoToMultipleUsers = photoToMultipleUsers;
    }

    public static class PhotoToMultipleUsers {
        private SendPhoto actualPhoto;
        private List<SendPhoto> receivers;

        public PhotoToMultipleUsers(SendPhoto actualPhoto, List<SendPhoto> receivers) {
            this.actualPhoto = actualPhoto;
            this.receivers = receivers;
        }

        public List<SendPhoto> getReceivers() {
            return receivers;
        }

        public SendPhoto getActualPhoto() {
            return actualPhoto;
        }
    }
}
