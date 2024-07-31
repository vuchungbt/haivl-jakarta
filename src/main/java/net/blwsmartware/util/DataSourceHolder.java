package net.blwsmartware.util;

import com.zaxxer.hikari.HikariDataSource;

public class DataSourceHolder {
    private static HikariDataSource dataSource;

    public static void setDataSource(HikariDataSource ds) {
        dataSource = ds;
    }

    public static HikariDataSource getDataSource() {
        return dataSource ;
    }
}
