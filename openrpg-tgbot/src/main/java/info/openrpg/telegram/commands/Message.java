package info.openrpg.telegram.commands;

import info.openrpg.telegram.io.InlineButton;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.function.Supplier;

public enum Message {
    HELP(() -> new SendMessage().setText("Все возможные команды").setReplyMarkup(InlineButton.helpInlineCommands())),
    MOVE_BUTTONS(() -> new SendMessage().setText("Выберите куда ходить").setReplyMarkup(InlineButton.moveButtonList())),
    CANT_CONNECT(() -> new SendMessage().setText("В данный момент сервер недоступен"));
    private final Supplier<SendMessage> sendMessage;

    Message(Supplier<SendMessage> sendMessage) {
        this.sendMessage = sendMessage;
    }

    public SendMessage sendTo(Long chatId) {
        return sendMessage.get().setChatId(chatId);
    }
}
