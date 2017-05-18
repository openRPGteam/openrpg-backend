package info.openrpg.database.repositories;

import com.google.inject.Inject;
import info.openrpg.database.models.Player;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PostgresPlayerDao implements PlayerDao {
    private final EntityManager entityManager;

    @Inject
    public PostgresPlayerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
