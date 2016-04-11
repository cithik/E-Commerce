package com.ecommerce.db;

import com.ecommerce.core.Orders;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class OrderDAO extends AbstractDAO<Orders> {
    public OrderDAO(SessionFactory factory) {
        super(factory);
    }

    public Orders create(Orders order) {
        return persist(order);
    }

    public List<Orders> findAll() {
        return list(namedQuery("com.ecommerce.core.Orders.findAll"));
    }
}
