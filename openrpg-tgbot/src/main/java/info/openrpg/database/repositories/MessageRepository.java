package info.openrpg.database.repositories;

import info.openrpg.database.models.Message;

public interface MessageRepository {
    void saveMessage(Message message);
}
