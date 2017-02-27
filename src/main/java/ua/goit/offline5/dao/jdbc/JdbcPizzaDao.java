package ua.goit.offline5.dao.jdbc;

import ua.goit.offline5.dao.PizzaDao;
import ua.goit.offline5.dao.model.Component;
import ua.goit.offline5.dao.model.Pizza;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andreymi on 2/27/2017.
 */
public class JdbcPizzaDao implements PizzaDao {
    private static final String GET_SQL = "select id, name, prize from pizza where id = ?";
    private static final String GET_COMPONENTS = "select id, name, prize " +
            " from components c join " +
            " pizza_components pc on (c.id=pc.component_id)" +
            " where pc.pizza_id = ?";
    private static final String INSERT_SQL = "insert into pizza(name, prize) values (?, ?)";
    private static final String INSERT_COMPONENT_SQL = "insert into " +
            "pizza_components(component_id, pizza_id) values (?, ?)";
    private static final String GET_LAST_INSERTED = "select LAST_INSERT_ID()";

    private DataSource dataSource;

    public JdbcPizzaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Pizza create(String name, BigDecimal price, Collection<Component> components) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {
                ps.setString(1, name);
                ps.setBigDecimal(2, price);
                ps.executeUpdate();
            }

            long id = 0;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_LAST_INSERTED)) {
                resultSet.next();
                id = resultSet.getLong(1);
            }

            //
            try (PreparedStatement ps =
                         connection.prepareStatement(INSERT_COMPONENT_SQL)) {
                for (Component component : components) {
                    ps.setLong(1, component.getId());
                    ps.setLong(2, id);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            //
            Pizza pizza = new Pizza();
            pizza.setId(id);
            pizza.setComponents(components);
            pizza.setName(name);
            pizza.setPrize(price);
            connection.commit();
            return pizza;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Pizza read(long id) {
        try (Connection connection = dataSource.getConnection()) {
            Pizza pizza;
            try (PreparedStatement ps = connection.prepareStatement(GET_SQL)) {
                ps.setLong(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    pizza = new Pizza();
                    pizza.setName(resultSet.getString("name"));
                    pizza.setPrize(resultSet.getBigDecimal("prize"));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(GET_COMPONENTS)) {
                ps.setLong(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    Collection<Component> components = new ArrayList<>();
                    while (resultSet.next()) {
                        Component component = new Component();
                        component.setId(resultSet.getLong("id"));
                        component.setName(resultSet.getString("name"));
                        component.setPrice(resultSet.getBigDecimal("prize"));
                        components.add(component);
                    }
                    pizza.setComponents(components);
                }
            }
            return pizza;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
