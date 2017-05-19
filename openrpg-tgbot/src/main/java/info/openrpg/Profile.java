package info.openrpg;

/**
 * @author Ignat Beresnev
 * @since 19.05.17
 */
public enum Profile {
    POSTGRES("application.properties"),
    HSQL("hsqldb.properties");

    public static final Profile ACTIVE_DB_PROFILE;
    static {
        ACTIVE_DB_PROFILE = getActiveDBProfile();
    }

    private String propertiesFileName;

    Profile(String propertiesFileName){
        this.propertiesFileName = propertiesFileName;
    }

    public String getPropertiesFileName() {
        return propertiesFileName;
    }

    public static Profile getActiveDBProfile(){
        try {
            Class.forName("org.postgresql.Driver");
            return POSTGRES;
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                return HSQL;
            } catch (ClassNotFoundException e1) {
                throw new IllegalStateException("Couldn't resolve DB profile");
            }
        }
    }
}
