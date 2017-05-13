package info.openrpg.telegram.commands.actions;

import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;

import java.util.List;

public interface ExecutableCommand {
    List<MessageWrapper> execute(InputMessage inputMessage);

    List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage);
}
