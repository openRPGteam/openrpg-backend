package info.openrpg.telegram.io;

import com.google.common.base.Joiner;
import info.openrpg.telegram.commands.TelegramCommand;
import lombok.Getter;
import org.telegram.telegrambots.api.objects.User;

import java.util.Arrays;
import java.util.Optional;

/**
 * Class that holds input from user
 */
@Getter
public class InputMessage {
    private final static Joiner JOINER = Joiner.on(" ").skipNulls();
    private final String text;
    private final Long chatId;
    private final User from;
    private final TelegramCommand command;
    private final String[] arguments;

    public InputMessage(String inputString, Long chatId, User from) {
        this.chatId = chatId;
        this.from = from;
        this.text = inputString;
        this.command = parseCommand(inputString);
        this.arguments = parseArguments(inputString);
    }

    public InputMessage(InputMessage inputMessage, String messageFromQueue) {
        this.chatId = inputMessage.chatId;
        this.from = inputMessage.from;
        this.text = JOINER.join(messageFromQueue, inputMessage.text);
        this.command = parseCommand(text);
        this.arguments = parseArguments(text);
    }

    private String[] parseArguments(String inputString) {
        return Optional.of(inputString)
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(arr -> arr.length > 1)
                .map(arr -> Arrays.copyOfRange(arr, 1, arr.length))
                .orElse(null);
    }

    private TelegramCommand parseCommand(String inputString) {
        return Optional.of(inputString)
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(arr -> arr.length > 0)
                .map(arr -> TelegramCommand.forCommand(arr[0]))
                .orElse(TelegramCommand.NOTHING);
    }

    public boolean hasArguments() {
        return arguments != null && arguments.length != 0;
    }

    public boolean hasArguments(int numberOfArguments) {
        return hasArguments() && arguments.length >= numberOfArguments;
    }

    public String getArgument(int number) {
        return arguments[number - 1];
    }

    public int size() {
        return arguments != null ? arguments.length : 0;
    }
}
