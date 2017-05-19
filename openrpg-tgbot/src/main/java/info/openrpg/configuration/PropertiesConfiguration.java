package info.openrpg.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;


public class PropertiesConfiguration {
    private static final String POSTGRES_PROPERTIES_NAME = "postgres.properties";
    private static final String HSQL_PROPERTIES_NAME = "hsqldb.properties";

    private static final String DEFAULT_APPLICATION_PROPERTIES_NAME = "application.properties";
    private static final String DEFAULT_DATABASE_PROPERTIES_NAME = POSTGRES_PROPERTIES_NAME;
    private static final String DEFAULT_HIBERNATE_PROPERTIES_NAME = "hibernate.properties";

    private static final Logger logger = Logger.getLogger(PropertiesConfiguration.class.getName());

    private static Properties application;
    private static Properties database;
    private static Properties hibernate;

    static {
        try {
            application = initFromResources(DEFAULT_APPLICATION_PROPERTIES_NAME);
            database = initFromResources(MavenProfile.ACTIVE_DB_PROFILE.getPropertiesFileName().orElse(DEFAULT_DATABASE_PROPERTIES_NAME));
            hibernate = initFromResources(DEFAULT_HIBERNATE_PROPERTIES_NAME);
        } catch (FileNotFoundException e) {
            logger.warning("Default properties file not found");
        } catch (IOException e) {
            logger.warning("Could not load default properties file");
        }
    }

    public static Properties initFromResources(String propertiesName) throws IOException {
        Properties properties = new Properties();
        properties.load(PropertiesConfiguration.class.getResourceAsStream(propertiesName));
        return properties;
    }

    /*
     * Все что ниже - задел на будущее. Если приспичит, можно пропарсить args и загрузить кастомные пропертис.
     */
    public static void setApplicationProperties(File file) throws IOException {
        application = initFromFile(file);
    }

    public static void setDatabaseProperties(File file) throws IOException {
        database = initFromFile(file);
    }

    public static void setHibernateProperties(File file) throws IOException {
        hibernate = initFromFile(file);
    }

    public static Properties initFromFile(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        return properties;
    }

    public static Properties getApplicationProperties() {
        return application;
    }

    public static Properties getDatabaseProperties() {
        return database;
    }

    public static Properties getHibernateProperties() {
        return hibernate;
    }

    public enum MavenProfile {
        POSTGRES(POSTGRES_PROPERTIES_NAME),
        HSQL(HSQL_PROPERTIES_NAME),
        NONE(null);

        public static final MavenProfile ACTIVE_DB_PROFILE;

        static {
            ACTIVE_DB_PROFILE = getActiveDBProfile();
        }

        private String propertiesFileName;

        MavenProfile(String propertiesFileName) {
            this.propertiesFileName = propertiesFileName;
        }

        public Optional<String> getPropertiesFileName() {
            return Optional.ofNullable(propertiesFileName);
        }

        public static MavenProfile getActiveDBProfile() {
            try {
                Class.forName("org.postgresql.Driver");
                return POSTGRES;
            } catch (ClassNotFoundException e) {
                try {
                    Class.forName("org.hsqldb.jdbcDriver");
                    return HSQL;
                } catch (ClassNotFoundException ee) {
                    return NONE;
                }
            }
        }
    }
}
