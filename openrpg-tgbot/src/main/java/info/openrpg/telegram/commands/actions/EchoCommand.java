package info.openrpg.telegram.commands.actions;

import info.openrpg.telegram.commands.MessageWrapper;
import info.openrpg.telegram.input.InputMessage;

import java.util.Collections;
import java.util.List;

public class EchoCommand implements ExecutableCommand {
    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return Collections.emptyList();
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }
}
