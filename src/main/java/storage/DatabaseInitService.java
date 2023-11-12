package storage;

import org.flywaydb.core.Flyway;

import java.util.ResourceBundle;

public class DatabaseInitService {
    public void initDb(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("hibernate");
        String url = resourceBundle.getString("hibernate.connection.url");
        String user = resourceBundle.getString("hibernate.connection.username");
        String password = resourceBundle.getString("hibernate.connection.password");

        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .load();
        flyway.migrate();
    }
}
