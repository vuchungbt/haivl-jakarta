package net.blwsmartware.util;

import com.zaxxer.hikari.HikariDataSource;

public class HikariCPMetrics {

    private HikariDataSource dataSource;

    public HikariCPMetrics(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void printMetrics() {
        System.out.println("-------------------------------\nActive Connections: " + dataSource.getHikariPoolMXBean().getActiveConnections());
        System.out.println("Idle Connections: " + dataSource.getHikariPoolMXBean().getIdleConnections());
        System.out.println("Total Connections: " + dataSource.getHikariPoolMXBean().getTotalConnections());
        System.out.println("Threads Waiting: " + dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
    }
}
