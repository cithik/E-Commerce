package com.ecommerce.resources;

;
import com.ecommerce.core.Orders;
import com.ecommerce.core.Product;
import com.ecommerce.core.ResourceMapping;
import com.ecommerce.db.ResourceMapppingDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by krithika on 12/11/15.
 */
@Path("/map")
@Produces(MediaType.APPLICATION_JSON)
public class GenericResource {

    private final ResourceMapppingDAO resourceMapppingDAO;
    private final ProductResource pr;
    private final OrderResource or;

    public GenericResource(ResourceMapppingDAO resourceMapppingDAO, ProductResource pr, OrderResource or) {
        this.resourceMapppingDAO = resourceMapppingDAO;
        this.pr = pr;
        this.or = or;
    }

    @POST
    @UnitOfWork
    public Object createMapping(ResourceMapping resourceMapping) {
        List<ResourceMapping> resources = resourceMapppingDAO.findAll();
        for(ResourceMapping rm: resources) {
            if (rm.getResource().equals(resourceMapping.getResource())) {
                rm.setReplace(resourceMapping.getReplace());
                return resourceMapppingDAO.create(rm);
            }
        }
        return resourceMapppingDAO.create(resourceMapping);
    }

    @Path("/{resource}")
    @POST
    @UnitOfWork
    public Object create(@PathParam("resource") String resource, String json) throws Exception{
        List<ResourceMapping> resources = resourceMapppingDAO.findAll();
        for(ResourceMapping rm: resources) {
            if (rm.getReplace().equals(resource)) {
                if(rm.getResource().equals("product")) {
                    ObjectMapper mapper = new ObjectMapper();
                    Product product = mapper.readValue(json, Product.class);
                    return pr.createProduct(product);
                } else if(rm.getResource().equals("order")) {
                        ObjectMapper mapper = new ObjectMapper();
                        Orders order = mapper.readValue(json, Orders.class);
                        return or.createOrder(order);
                }
            }
        }
        return new Exception("Can't find");
    }

    @Path("/{resource}")
    @GET
    @UnitOfWork
    public Object find(@PathParam("resource") String resource) throws Exception{
        List<ResourceMapping> resources = resourceMapppingDAO.findAll();
        for(ResourceMapping rm: resources) {
            if (rm.getReplace().equals(resource)) {
                if(rm.getResource().equals("product")) {
                    return pr.listProduct();
                } else if(rm.getResource().equals("order")) {
                    return or.listOrder();
                }
            }
        }
        return new Exception("Can't find");
    }

    @GET
    @UnitOfWork
    public List<ResourceMapping> listMappings() {
        return resourceMapppingDAO.findAll();
    }
}
