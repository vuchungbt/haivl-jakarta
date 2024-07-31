package net.blwsmartware.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.ResourceBundle;


public class HikariCPDataSource {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

        config.setJdbcUrl(resourceBundle.getString("url"));
        config.setUsername(resourceBundle.getString("user"));
        config.setPassword(resourceBundle.getString("password"));
        config.setDriverClassName(resourceBundle.getString("driverName"));

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(3);
        config.setConnectionTimeout(5000);

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
