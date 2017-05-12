package info.openrpg.database.repositories;

import info.openrpg.database.models.Message;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;

@AllArgsConstructor
public class PostrgresMessageRepository implements MessageRepository {
    private final EntityManager entityManager;

    @Override
    public void saveMessage(Message message) {
        entityManager.persist(message);
    }
}
