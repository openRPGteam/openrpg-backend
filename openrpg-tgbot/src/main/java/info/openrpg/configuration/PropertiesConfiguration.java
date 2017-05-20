package info.openrpg.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
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

    public static void initializeDefaults() {
        logger.info("Initializing defaults");
        try {
            if (application == null)
                application = initFromResources(DEFAULT_APPLICATION_PROPERTIES_NAME);
            if (database == null)
                database = initFromResources(MavenProfile.ACTIVE_DB_PROFILE.getPropertiesFileName().orElse(DEFAULT_DATABASE_PROPERTIES_NAME));
            if (hibernate == null)
                hibernate = initFromResources(DEFAULT_HIBERNATE_PROPERTIES_NAME);
        } catch (FileNotFoundException e) {
            logger.warning("Default properties file not found");
        } catch (IOException e) {
            logger.warning("Could not load default properties file");
        }
    }

    public static Properties initFromResources(String propertiesName) throws IOException {
        Properties properties = new Properties();
        properties.load(PropertiesConfiguration.class.getClassLoader().getResourceAsStream(propertiesName));
        return properties;
    }

    /*
     * Dproperties, Ddatabase и Dhibernate - опционально. Если их нет, делается дефолт.
     * Чтобы сделать все три обязательно, уберите проверку на null и раскомментить чек на нулл.
     * Чтобы совсем убрать дефолтные настройки, удалите метод initializeDefaults и стринг константы с именами файлов.
     */
    public static void setApplicationProperties(File file) throws IOException {
//        requirePropertiesFileNotNull(file, "applicatoin");
        if (file != null)
            application = initFromFile(file);
    }

    public static void setDatabaseProperties(File file) throws IOException {
//        requirePropertiesFileNotNull(file, "database");
        if (file != null)
            database = initFromFile(file);
    }

    public static void setHibernateProperties(File file) throws IOException {
//        requirePropertiesFileNotNull(file, "hibernate");
        if (file != null)
            hibernate = initFromFile(file);
    }

    public static Properties initFromFile(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        return properties;
    }

    public static void initFromArgs(Map<String, String> args) throws IOException {
        if (args != null) {
            setApplicationProperties(getExistingFileFromPath(args.get("-Dproperties")));
            setDatabaseProperties(getExistingFileFromPath(args.get("-Ddatabase")));
            setHibernateProperties(getExistingFileFromPath(args.get("-Dhibernate")));
        }
        initializeDefaults();
    }

    public static File getExistingFileFromPath(String path) {
        if (path != null && path.endsWith(".properties")) {
            File file = new File(path);
            if (file.exists())
                return file;
        }
        return null;
    }

    public static void requirePropertiesFileNotNull(File file, String properties) {
        if (file == null)
            throw new IllegalStateException(
                    "Provided " + properties + " properties file doesn't exist or doesn't end with .properties");
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
