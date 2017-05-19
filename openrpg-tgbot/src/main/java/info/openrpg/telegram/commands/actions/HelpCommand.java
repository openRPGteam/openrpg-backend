package info.openrpg.telegram.commands.actions;

import info.openrpg.gameserver.WorldInstance;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.io.InputMessage;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements ExecutableCommand {

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage, WorldInstance worldInstance) {
        return Collections.singletonList(new MessageWrapper(Message.HELP.sendTo(inputMessage.getChatId())));
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage, WorldInstance worldInstance) {
        return Collections.emptyList();
    }
}
