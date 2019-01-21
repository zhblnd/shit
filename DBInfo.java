package database;

import org.hibernate.cfg.Configuration;

public class DBInfo {
    private final String user;
    private final String password;

    public DBInfo(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.username", this.getUser())
                .setProperty("hibernate.connection.password", this.getPassword());
        configuration.configure();
//        Configuration configuration = new Configuration().configure("/resources/hibernate.cfg.xml");
        return configuration;
    }
}
