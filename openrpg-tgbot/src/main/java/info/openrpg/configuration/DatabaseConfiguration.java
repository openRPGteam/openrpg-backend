package info.openrpg.configuration;

import com.google.inject.AbstractModule;
import info.openrpg.database.repositories.MessageDao;
import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.database.repositories.PostgresMessageDao;
import info.openrpg.database.repositories.PostgresPlayerDao;

public class DatabaseConfiguration extends AbstractModule{


    @Override
    protected void configure() {
        bind(PlayerDao.class).to(PostgresPlayerDao.class);
        bind(MessageDao.class).to(PostgresMessageDao.class);
    }
}
