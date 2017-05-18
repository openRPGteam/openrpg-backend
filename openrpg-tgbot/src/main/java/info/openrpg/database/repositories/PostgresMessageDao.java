package info.openrpg.database.repositories;

import com.google.inject.Inject;
import info.openrpg.database.models.Message;

import javax.persistence.EntityManager;

public class PostgresMessageDao implements MessageDao {
    private final EntityManager entityManager;

    @Inject
    public PostgresMessageDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveMessage(Message message) {
        entityManager.persist(message);
    }
}
