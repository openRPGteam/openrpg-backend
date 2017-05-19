package info.openrpg.telegram.commands.actions;

import info.openrpg.gameserver.WorldInstance;
import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;

import java.util.List;

public interface ExecutableCommand {
    List<MessageWrapper> execute(InputMessage inputMessage, WorldInstance worldInstance);

    List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage, WorldInstance worldInstance);
}
