package info.openrpg.telegram.input;

import com.google.common.base.Joiner;
import info.openrpg.telegram.commands.CommandChooser;
import info.openrpg.telegram.commands.TelegramCommand;
import lombok.Getter;
import org.telegram.telegrambots.api.objects.User;

import java.util.Arrays;
import java.util.Optional;

@Getter
public class InputMessage {
    private final static Joiner JOINER = Joiner.on(" ").skipNulls();
    private final String text;
    private final Long chatId;
    private final User from;
    private final TelegramCommand command;
    private final String[] arguments;

    public InputMessage(String inputString, Long chatId, User from, CommandChooser commandChooser) {
        this.chatId = chatId;
        this.from = from;
        this.text = inputString;
        this.command = Optional.of(inputString)
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(arr -> arr.length > 0)
                .map(arr -> commandChooser.chooseCommand(arr[0]))
                .orElse(TelegramCommand.NOTHING);
        this.arguments = Optional.of(inputString)
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(arr -> arr.length > 1)
                .map(arr -> Arrays.copyOfRange(arr, 1, arr.length))
                .orElse(null);
    }

    public InputMessage(InputMessage inputMessage, String messageFromQueue, CommandChooser commandChooser) {
        this.chatId = inputMessage.chatId;
        this.from = inputMessage.from;
        this.text = JOINER.join(messageFromQueue, inputMessage.text);
        this.command = Optional.of(text)
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(arr -> arr.length > 0)
                .map(arr -> commandChooser.chooseCommand(arr[0]))
                .orElse(TelegramCommand.NOTHING);
        this.arguments = Optional.of(text)
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(arr -> arr.length > 1)
                .map(arr -> Arrays.copyOfRange(arr, 1, arr.length))
                .orElse(null);
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
