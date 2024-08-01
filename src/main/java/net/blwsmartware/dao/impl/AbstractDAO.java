package net.blwsmartware.dao.impl;


import net.blwsmartware.dao.GenericDAO;
import net.blwsmartware.mapper.RowMapper;
import net.blwsmartware.util.HikariCPDataSource;
import net.blwsmartware.util.HikariCPMetrics;
import org.apache.commons.text.StringEscapeUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO implements GenericDAO {

    private void setParameter(PreparedStatement statement, Object... param) {
        try {
            for (int i = 0; i < param.length; i++) {
                Object parameter = param[i];
                int index = i + 1;
                switch (parameter) {
                    case Long l -> statement.setLong(index, l);
                    case String s -> statement.setString(index, s);
                    case Integer integer -> statement.setInt(index, integer);
                    case Timestamp timestamp -> statement.setTimestamp(index, timestamp);
                    case null -> statement.setNull(index, Types.NULL);
                    default -> {
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("setParameter error:" + e.getMessage());
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... param) {
        List<T> results = new ArrayList<>();
        System.out.println("Query:" + sql);

        try (
                Connection connection = HikariCPDataSource.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql))
        ) {
            setParameter(statement, param);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
                // new HikariCPMetrics(HikariCPDataSource.getDataSource()).printMetrics();
            }
        } catch (SQLException e) {
            System.out.println("ResultSet query error:" + e.getMessage());
            return null;
        }
        return results;

    }

    @Override
    public void update(String sql, Object... param) {

        Connection connection = null;
        try {
            connection = HikariCPDataSource.getDataSource().getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql))) {
                setParameter(statement, param);
                statement.executeUpdate();
                connection.commit();
            }

        } catch (SQLException e) {
            System.out.println("ResultSet update error:" + e.getMessage());
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException e1) {
                System.out.println("ResultSet update error:" + e1.getMessage());
            }
        }
    }

    @Override
    public Long insert(String sql, Object... param) {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getDataSource().getConnection();
            Long id = null;
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql), Statement.RETURN_GENERATED_KEYS)) {
                setParameter(statement, param);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    }
                    connection.commit();
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println("ResultSet update error:" + e.getMessage());
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException e1) {
                System.out.println("ResultSet update error:" + e1.getMessage());
            }
        }
        return null;
    }

    @Override
    public void delete(String sql, Object... param) {
        this.update(StringEscapeUtils.escapeHtml4(sql), param);
    }

    @Override
    public int count(String sql, Object... param) {
        int count = 0;
        try (
                Connection connection = HikariCPDataSource.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql));
        ) {
            setParameter(statement, param);
            try (
                    ResultSet resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("ResultSet count error:" + e.getMessage());
        }
        return count;
    }

    @Override
    public <T> T findOne(String sql, RowMapper<T> rowMapper, Object... param) {

        try (
                Connection connection = HikariCPDataSource.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql));
        ) {
            setParameter(statement, param);
            try (
                    ResultSet resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return rowMapper.mapRow(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("ResultSet findOne error:" + e.getMessage());
        }
        return null;

    }
}