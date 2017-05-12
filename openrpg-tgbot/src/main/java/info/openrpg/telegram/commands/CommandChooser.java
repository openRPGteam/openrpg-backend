package info.openrpg.telegram.commands;

import info.openrpg.constants.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandChooser {
    private Map<String, TelegramCommand> commandMap;

    public CommandChooser() {
        this.commandMap = new HashMap<>();
        commandMap.put(Commands.HELP, TelegramCommand.HELP);
        commandMap.put(Commands.PLAYER_INFO, TelegramCommand.PLAYER_INFO);
        commandMap.put(Commands.START, TelegramCommand.START);
        commandMap.put(Commands.PEEK_PLAYER, TelegramCommand.PEEK_PLAYER);
        commandMap.put(Commands.SEND_MESSAGE, TelegramCommand.SEND_MESSAGE);
        commandMap.put(Commands.SPAWN, TelegramCommand.SPAWN);
        commandMap.put(Commands.MOVE, TelegramCommand.MOVE);
    }

    public TelegramCommand chooseCommand(String rawText) {
        return Optional.of(rawText)
                .filter(text -> text.startsWith("/"))
                .map(text -> commandMap.get(text))
                .orElse(TelegramCommand.NOTHING);
    }
}
