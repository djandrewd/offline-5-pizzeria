package ua.goit.offline5.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.goit.offline5.dao.model.Component;
import ua.goit.offline5.dao.model.Pizza;

/**
 * Created by andreymi on 3/10/2017.
 */
public class Loader {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration()
                 .addAnnotatedClass(Component.class)
                 .addAnnotatedClass(Pizza.class)
                 .configure("hibernate.cfg.xml")
                 .buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                Pizza pizza = session.get(Pizza.class, 1);
                System.out.println(pizza);
            }
        }



    }
}
