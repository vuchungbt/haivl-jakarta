package net.blwsmartware.filter;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import net.blwsmartware.util.DataSourceHolder;
import net.blwsmartware.util.HikariCPDataSource;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;


@WebListener
public class DBDriverListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DBDriverListener.class.getName());

    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSourceHolder.setDataSource(HikariCPDataSource.getDataSource());
        dataSource = DataSourceHolder.getDataSource();
        if (dataSource != null) LOGGER.info("-------Registered JDBC driver and init HikariDataSource -----------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
            LOGGER.info("---HikariDataSource: closed");
        }

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        LOGGER.info("---has Driver:" + drivers.hasMoreElements());

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                AbandonedConnectionCleanupThread.checkedShutdown();
                LOGGER.info("---Deregistered JDBC driver: " + driver);
            } catch (SQLException e) {
                LOGGER.severe("---Error deregistering driver: " + driver + e);
            }
        }
    }
}