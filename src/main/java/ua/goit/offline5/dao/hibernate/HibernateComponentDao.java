package ua.goit.offline5.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.goit.offline5.dao.ComponentDao;
import ua.goit.offline5.dao.model.Component;

import javax.el.Expression;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by andreymi on 3/17/2017.
 */
public class HibernateComponentDao implements ComponentDao {
    private SessionFactory sessionFactory;

    public HibernateComponentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Component read(long id) {
        return null;
    }

    @Override
    public Component create(String name, BigDecimal price) {
        return null;
    }

    @Override
    public Component topPrice() {
        try (Session session = sessionFactory.openSession()) {
            List<Component> components = (List<Component>)
                    session.createQuery("from Component order by price desc").list();
            if (components != null && !components.isEmpty()) {
                return components.get(0);
            }
        }
        return null;
    }
}
