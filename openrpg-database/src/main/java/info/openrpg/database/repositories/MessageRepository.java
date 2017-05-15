package info.openrpg.database.repositories;

import info.openrpg.database.models.telegram.Message;

public interface MessageRepository {
    void saveMessage(Message message);
}
