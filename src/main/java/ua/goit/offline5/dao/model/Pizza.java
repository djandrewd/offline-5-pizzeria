package ua.goit.offline5.dao.model;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by andreymi on 2/27/2017.
 */
public class Pizza {
    private long id;
    private String name;
    private BigDecimal prize;
    private Collection<Component> components;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    public Collection<Component> getComponents() {
        return components;
    }

    public void setComponents(Collection<Component> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prize=" + prize +
                ", components=" + components +
                '}';
    }
}
