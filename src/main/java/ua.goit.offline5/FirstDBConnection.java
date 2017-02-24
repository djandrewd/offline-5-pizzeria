package ua.goit.offline5;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/pizzeria");
        dataSource.setUsername("root");
        dataSource.setPassword("5652388");
        dataSource.setMinIdle(5);

        try(Connection connection = dataSource.getConnection()) {
            //SELECT name, prize FROM pizzeria.components
            String name = "va";
            BigDecimal price = BigDecimal.ONE;
            //
            try (PreparedStatement statement  = connection
               .prepareStatement("insert into components (name, prize) values (?, ?)")) {
                connection.setAutoCommit(false);
                try {
                    statement.setString(1, "va2");
                    statement.setBigDecimal(2, price);
                    statement.executeUpdate();
                    connection.commit();
                } catch (Exception e) {
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
