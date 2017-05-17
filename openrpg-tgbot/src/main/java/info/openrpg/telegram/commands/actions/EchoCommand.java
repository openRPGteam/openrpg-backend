package info.openrpg.telegram.commands.actions;

import info.openrpg.image.processing.RequstSender;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

public class EchoCommand implements ExecutableCommand {
    private final RequstSender requstSender;

    public EchoCommand(RequstSender requstSender) {
        this.requstSender = requstSender;
    }

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return Collections.singletonList(
                new MessageWrapper(
                        new SendMessage()
                                .setText(requstSender.ping())
                                .setChatId(inputMessage.getChatId())));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }
}
