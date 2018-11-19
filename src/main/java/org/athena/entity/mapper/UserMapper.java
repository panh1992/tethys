package org.athena.entity.mapper;

import org.athena.entity.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet, StatementContext ctx) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassWord(resultSet.getString("password"));
        return user;
    }

}
