package com.ecommerce.resources;

import com.ecommerce.core.Product;
import com.ecommerce.db.ProductDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductDAO productDAO;

    public ProductResource(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @POST
    @UnitOfWork
    public Product createProduct(Product product) {
        return productDAO.create(product);
    }


    @GET
    @UnitOfWork
    public List<Product> listProduct() {
        return productDAO.findAll();
    }

}
