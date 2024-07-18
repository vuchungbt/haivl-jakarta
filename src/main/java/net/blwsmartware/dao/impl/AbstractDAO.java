package net.blwsmartware.dao.impl;


import net.blwsmartware.dao.GenericDAO;
import net.blwsmartware.mapper.RowMapper;
import org.apache.commons.lang3.StringEscapeUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AbstractDAO<T> implements GenericDAO<T> {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    public Connection getConnection() {

        try {
            Class.forName(resourceBundle.getString("driverName"));
            String url = resourceBundle.getString("url");
            String user = resourceBundle.getString("user");
            String password = resourceBundle.getString("password");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connect error:" + e.getMessage());
            return null;
        }
    }

    private void setParameter(PreparedStatement statement, Object... param) {
        try {
            for (int i = 0; i < param.length; i++) {
                Object parameter = param[i];
                int index = i + 1;
                if (parameter instanceof Long) {
                    statement.setLong(index, (Long) parameter);
                } else if (parameter instanceof String) {
                    statement.setString(index, (String) parameter);
                } else if (parameter instanceof Integer) {
                    statement.setInt(index, (Integer) parameter);
                } else if (parameter instanceof Timestamp) {
                    statement.setTimestamp(index, (Timestamp) parameter);
                }else if(parameter == null) {
                    statement.setNull(i+1, Types.NULL);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... param) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            System.out.println("Query:"+sql);
            connection = getConnection();
            statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql));
            setParameter(statement, param);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet));
            }
            return results;
        } catch (SQLException e) {
            System.out.println("ResultSet error:" + e.getMessage());
            return null;
            //throw new RuntimeException(e);

        }
    }

    @Override
    public void update(String sql, Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql));
            setParameter(statement, param);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public Long insert(String sql, Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Long id = null;
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql), Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, param);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            System.out.println("Save error:"+e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int count = 0;
            connection = getConnection();
            statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql));
            setParameter(statement, param);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            return 0;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                return 0;
            }
        }
    }

    @Override
    public <T> T findOne(String sql, RowMapper<T> rowMapper, Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        System.out.println(sql);
        try {
            connection = getConnection();
            statement = connection.prepareStatement(StringEscapeUtils.escapeHtml4(sql));
            setParameter(statement, param);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return rowMapper.mapRow(resultSet);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("ResultSet error:" + e.getMessage());
            return null;

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                return null;
            }


        }


    }
}