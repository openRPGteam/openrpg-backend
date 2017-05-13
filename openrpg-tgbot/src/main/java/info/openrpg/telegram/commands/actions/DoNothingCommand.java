package info.openrpg.telegram.commands.actions;

import info.openrpg.telegram.io.MessageWrapper;
import info.openrpg.telegram.io.InputMessage;

import java.util.Collections;
import java.util.List;

public class DoNothingCommand implements ExecutableCommand {

    @Override
    public List<MessageWrapper> execute(InputMessage inputMessage) {
        return Collections.emptyList();
    }

    @Override
    public List<MessageWrapper> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }
}
