package info.openrpg.database.repositories;

import info.openrpg.database.models.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> findPlayerByUsername(String username);

    List<Player> selectPlayerWithOffset(int offset, int count);

    int selectPlayersNumber();

    void savePlayer(Player player);
}
