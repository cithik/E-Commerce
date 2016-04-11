package com.ecommerce.db;

import com.ecommerce.core.Product;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class ProductDAO extends AbstractDAO<Product> {
    public ProductDAO(SessionFactory factory) {
        super(factory);
    }

    public Product create(Product product) {
        return persist(product);
    }

    public List<Product> findAll() {
        return list(namedQuery("com.ecommerce.core.Product.findAll"));
    }
}
