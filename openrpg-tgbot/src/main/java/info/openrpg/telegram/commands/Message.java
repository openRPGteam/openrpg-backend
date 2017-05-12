package info.openrpg.telegram.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public enum Message {
    HELP(new SendMessage().setText("Все возможные команды").setReplyMarkup(InlineCommand.helpInlineCommands()));
    private final SendMessage sendMessage;

    Message(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public SendMessage sendTo(Long chatId) {
        return sendMessage.setChatId(chatId);
    }
}
