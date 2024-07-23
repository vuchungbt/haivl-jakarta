package net.blwsmartware.filter;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;


@WebListener
public class DBDriverListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DBDriverListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("-----Registered JDBC driver--------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        LOGGER.info("-----has Driver:"+drivers.hasMoreElements());
        while (drivers.hasMoreElements()) {
            Driver driver =drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                AbandonedConnectionCleanupThread.checkedShutdown();
                System.out.println("Deregistered JDBC driver: " + driver);
            } catch (SQLException e) {
                System.out.println("Error deregistering driver: " + driver + e);
            }
        }
    }
}