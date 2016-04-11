package com.ecommerce.db;

import com.ecommerce.core.ResourceMapping;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by krithika on 12/11/15.
 */
public class ResourceMapppingDAO extends AbstractDAO<ResourceMapping> {
    public ResourceMapppingDAO(SessionFactory factory) {
        super(factory);
    }

    public ResourceMapping create(ResourceMapping resourceMapping) {
        return persist(resourceMapping);
    }
    public List<ResourceMapping> findAll() {
        return list(namedQuery("com.ecommerce.core.ResourceMapping.findAll"));
    }
}
