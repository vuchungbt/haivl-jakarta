package net.blwsmartware.dao.impl;

import net.blwsmartware.dao.GenericDAO;
import net.blwsmartware.mapper.RowMapper;
import net.blwsmartware.util.HikariCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO implements GenericDAO {

    private void setParameter(PreparedStatement statement, Object... param) throws SQLException {
        for (int i = 0; i < param.length; i++) {
            Object parameter = param[i];
            int index = i + 1;
            if (parameter == null) {
                statement.setNull(index, Types.NULL);
            } else if (parameter instanceof Long) {
                statement.setLong(index, (Long) parameter);
            } else if (parameter instanceof String) {
                statement.setString(index, (String) parameter);
            } else if (parameter instanceof Integer) {
                statement.setInt(index, (Integer) parameter);
            } else if (parameter instanceof Timestamp) {
                statement.setTimestamp(index, (Timestamp) parameter);
            }
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... param) {
        List<T> results = new ArrayList<>();
        System.out.println("Query:" + sql);

        try (
                Connection connection = HikariCPDataSource.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            setParameter(statement, param);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("ResultSet query error:" + e.getMessage());
            return null;
        }
        return results;
    }

    @Override
    public void update(String sql, Object... param) {
        try (Connection connection = HikariCPDataSource.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, param);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("ResultSet update error:" + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Connection error:" + e.getMessage());
        }
    }

    @Override
    public Long insert(String sql, Object... param) {
        try (Connection connection = HikariCPDataSource.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParameter(statement, param);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        connection.commit();
                        return resultSet.getLong(1);
                    }
                }
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("ResultSet insert error:" + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Connection error:" + e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String sql, Object... param) {
        update(sql, param);
    }

    @Override
    public int count(String sql, Object... param) {
        int count = 0;
        try (
                Connection connection = HikariCPDataSource.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            setParameter(statement, param);
            try (ResultSet resultSet = statement.executeQuery()) {
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
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            setParameter(statement, param);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return rowMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("ResultSet findOne error:" + e.getMessage());
        }
        return null;
    }

    @Override
    public void insertNotReturn(String sql, Object... param){
        try(Connection connection = HikariCPDataSource.getDataSource().getConnection()){
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)){
                setParameter(statement, param);
                statement.executeUpdate();
                connection.commit();
            }
        }catch (SQLException e){
            System.out.println("Connection error:" + e.getMessage());
        }
    }
}
