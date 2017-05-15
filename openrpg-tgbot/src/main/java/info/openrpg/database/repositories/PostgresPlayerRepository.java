package info.openrpg.database.repositories;

import info.openrpg.database.models.Player;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PostgresPlayerRepository implements PlayerRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Player> findPlayerByUsername(String username) {
        return entityManager.createQuery("from Player p where p.userName = :userName", Player.class)
                .setParameter("userName", username)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Player> selectPlayerWithOffset(int offset, int count) {
        return entityManager.createQuery("from Player p ", Player.class)
                .getResultList();
    }

    @Override
    public int selectPlayersNumber() {
        return entityManager.createQuery("select count(*) from Player", Long.class)
                .getFirstResult();
    }

    @Override
    public void savePlayer(Player player) {
        entityManager.persist(player);
    }
}
