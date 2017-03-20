package ua.goit.offline5.dao;

import ua.goit.offline5.dao.model.Component;

import java.math.BigDecimal;

/**
 * Created by andreymi on 2/27/2017.
 */
public interface ComponentDao {
    Component read(long id);
    Component create(String name, BigDecimal price);
    // Method return component with max value.
    Component topPrice();
}
