package ua.goit.offline5.dao;

import ua.goit.offline5.dao.model.Component;
import ua.goit.offline5.dao.model.Pizza;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by andreymi on 2/27/2017.
 */
public interface PizzaDao {
    Pizza read(long id);

    Pizza create(String name, BigDecimal price,
                 Collection<Component> components);
}
