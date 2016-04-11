package com.ecommerce.resources;

import com.ecommerce.core.Orders;
import com.ecommerce.db.OrderDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderDAO orderDAO;

    public OrderResource(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @POST
    @UnitOfWork
    public Orders createOrder(Orders order) {
        return orderDAO.create(order);
    }


    @GET
    @UnitOfWork
    public List<Orders> listOrder() {
        return orderDAO.findAll();
    }

}
