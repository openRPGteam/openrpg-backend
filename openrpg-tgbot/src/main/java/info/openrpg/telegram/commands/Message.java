package info.openrpg.telegram.commands;

import info.openrpg.telegram.io.InlineButton;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public enum Message {
    HELP(new SendMessage().setText("Все возможные команды").setReplyMarkup(InlineButton.helpInlineCommands())),
    MOVE_BUTTONS(new SendMessage().setText("Выберите куда ходить").setReplyMarkup(InlineButton.moveButtonList()));
    private final SendMessage sendMessage;

    Message(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public SendMessage sendTo(Long chatId) {
        return sendMessage.setChatId(chatId);
    }
}
