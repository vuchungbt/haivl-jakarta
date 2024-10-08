package net.blwsmartware.dao;


import net.blwsmartware.mapper.RowMapper;

import java.util.List;

public interface GenericDAO {

    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... param);

    void update(String sql, Object... param);

    Long insert(String sql, Object... param);

    void delete(String sql, Object... param);

    int count(String sql, Object... param);

    <T> T findOne(String sql, RowMapper<T> rowMapper, Object... param);

    void insertNotReturn(String sql, Object... param);
}
