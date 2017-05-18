package info.openrpg.database.repositories;

import info.openrpg.database.models.Message;

public interface MessageDao {
    void saveMessage(Message message);
}
