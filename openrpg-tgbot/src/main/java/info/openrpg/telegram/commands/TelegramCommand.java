package info.openrpg.telegram.commands;

import info.openrpg.database.repositories.PostgresPlayerRepository;
import info.openrpg.database.repositories.PostrgresMessageRepository;
import info.openrpg.telegram.commands.actions.*;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.function.Function;

@AllArgsConstructor
public enum TelegramCommand {
    NOTHING(entityManager -> new DoNothingComand()),
    START(entityManager -> new StartCommand(new PostgresPlayerRepository(entityManager))),
    HELP(entityManager -> new HelpCommand()),
    PLAYER_INFO(entityManager -> new PlayerInfoCommand(new PostgresPlayerRepository(entityManager))),
    PEEK_PLAYER(entityManager -> new PeekPlayerCommand(new PostgresPlayerRepository(entityManager))),
    SEND_MESSAGE(entityManager ->
            new SendMessageCommand(
                    new PostgresPlayerRepository(entityManager),
                    new PostrgresMessageRepository(entityManager)
            )
    ),
    SPAWN(entityManager -> new SpawnCommand(new PostgresPlayerRepository(entityManager))),
    MOVE(entityManager -> new MoveCommand(new PostgresPlayerRepository(entityManager)));

    private Function<EntityManager, ExecutableCommand> executableCommand;

    public ExecutableCommand getExecutableCommand(EntityManager entityManager) {
        return executableCommand.apply(entityManager);
    }
}