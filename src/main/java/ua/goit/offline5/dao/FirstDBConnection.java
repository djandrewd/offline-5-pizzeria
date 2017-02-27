package ua.goit.offline5.dao;

import org.apache.commons.dbcp.BasicDataSource;
import ua.goit.offline5.dao.jdbc.JdbcPizzaDao;

import java.sql.SQLException;

/**
 * Created by andreymi on 2/24/2017.
 */
public class FirstDBConnection {
//
//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void main(String[] args) throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/pizzeria");
        dataSource.setUsername("root");
        dataSource.setPassword("5652388");
        dataSource.setMinIdle(5);

        //ComponentDao componentDao = new JdbcComponentDao(dataSource);
        //Component component = componentDao.read(1);
        //System.out.println(component);
        //System.out.println(componentDao.create("my new", BigDecimal.ONE));
        PizzaDao pizzaDao = new JdbcPizzaDao(dataSource);
        System.out.println(pizzaDao.read(1));

        dataSource.close();
    }

}
