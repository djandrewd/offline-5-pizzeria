package ua.goit.offline5.dao.jdbc;

import ua.goit.offline5.dao.ComponentDao;
import ua.goit.offline5.dao.model.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by andreymi on 2/27/2017.
 */
public class JdbcComponentDao implements ComponentDao {
    private static final String GET_SQL = "select id, name, prize from components where id = ?";
    private static final String CREATE_SQL = "insert into components(name, prize) values (?, ?)";
    private static final String GET_LAST_INSERTED = "select LAST_INSERT_ID()";

    private DataSource dataSource;

    public JdbcComponentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Component read(long id) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_SQL)) {
                ps.setLong(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    String name = resultSet.getString("name");
                    BigDecimal price = resultSet.getBigDecimal("prize");
                    Component component = new Component();
                    component.setId(id);
                    component.setName(name);
                    component.setPrice(price);
                    return component;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Component create(String name, BigDecimal price) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(CREATE_SQL)) {
                ps.setString(1, name);
                ps.setBigDecimal(2, price);
                ps.executeUpdate();
            }

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_LAST_INSERTED)) {
                resultSet.next();
                Component component = new Component();
                component.setId(resultSet.getLong(1));
                component.setName(name);
                component.setPrice(price);
                return component;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
