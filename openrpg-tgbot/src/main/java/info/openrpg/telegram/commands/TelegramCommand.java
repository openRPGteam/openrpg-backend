package info.openrpg.telegram.commands;

import info.openrpg.database.repositories.PostgresPlayerDao;
import info.openrpg.database.repositories.PostgresMessageDao;
import info.openrpg.image.processing.HTTPRequestSender;
import info.openrpg.telegram.commands.actions.DoNothingCommand;
import info.openrpg.telegram.commands.actions.ExecutableCommand;
import info.openrpg.telegram.commands.actions.HelpCommand;
import info.openrpg.telegram.commands.actions.MoveCommand;
import info.openrpg.telegram.commands.actions.PeekPlayerCommand;
import info.openrpg.telegram.commands.actions.PlayerInfoCommand;
import info.openrpg.telegram.commands.actions.SendMessageCommand;
import info.openrpg.telegram.commands.actions.SpawnCommand;
import info.openrpg.telegram.commands.actions.StartCommand;
import info.openrpg.telegram.constants.Command;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 */
@AllArgsConstructor
public enum TelegramCommand {
    NOTHING("", entityManager -> new DoNothingCommand()),
    START(Command.START, entityManager -> new StartCommand(new PostgresPlayerDao(entityManager), new HTTPRequestSender())),
    HELP(Command.HELP, entityManager -> new HelpCommand()),
    PLAYER_INFO(Command.PLAYER_INFO, entityManager -> new PlayerInfoCommand(new PostgresPlayerDao(entityManager))),
    PEEK_PLAYER(Command.PEEK_PLAYER, entityManager -> new PeekPlayerCommand(new PostgresPlayerDao(entityManager))),
    SEND_MESSAGE(Command.SEND_MESSAGE, entityManager ->
            new SendMessageCommand(
                    new PostgresPlayerDao(entityManager),
                    new PostgresMessageDao(entityManager)
            )
    ),
    SPAWN(Command.SPAWN, entityManager -> new SpawnCommand(new PostgresPlayerDao(entityManager), new HTTPRequestSender())),
    MOVE(Command.MOVE, entityManager -> new MoveCommand(new PostgresPlayerDao(entityManager), new HTTPRequestSender()));

    private final String commandPrefix;
    private final Function<EntityManager, ExecutableCommand> executableCommand;

    public ExecutableCommand getExecutableCommand(EntityManager entityManager) {
        return executableCommand.apply(entityManager);
    }


    /**
     * Find command by user input
     * @param inputString first word of user io
     */
    public static TelegramCommand forCommand(String inputString) {
        return Stream.of(TelegramCommand.values())
                .filter(value -> value.commandPrefix.equals(inputString))
                .findFirst()
                .orElse(TelegramCommand.NOTHING);
    }
}