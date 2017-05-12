package info.openrpg.telegram.commands.actions;

import info.openrpg.telegram.commands.MessageWrapper;
import info.openrpg.telegram.input.InputMessage;

import java.util.List;

public interface ExecutableCommand {
    List<MessageWrapper> execute(InputMessage inputMessage);

    List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage);
}
