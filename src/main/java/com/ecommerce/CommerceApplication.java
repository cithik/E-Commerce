package com.ecommerce;
import com.ecommerce.core.Orders;
import com.ecommerce.core.Product;
import com.ecommerce.core.ResourceMapping;
import com.ecommerce.db.OrderDAO;
import com.ecommerce.db.ProductDAO;
import com.ecommerce.db.ResourceMapppingDAO;
import com.ecommerce.resources.GenericResource;
import com.ecommerce.resources.OrderResource;
import com.ecommerce.resources.ProductResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import java.util.Map;

public class CommerceApplication extends Application<CommerceConfiguration> {
    public static void main(String[] args) throws Exception {
        new CommerceApplication().run(args);
    }

    private final HibernateBundle<CommerceConfiguration> hibernateBundle =
            new HibernateBundle<CommerceConfiguration>(Product.class, Orders.class, ResourceMapping.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(CommerceConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "e-Commerce";
    }

    @Override
    public void initialize(Bootstrap<CommerceConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<CommerceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(CommerceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle<CommerceConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(CommerceConfiguration configuration) {
                return configuration.getViewRendererConfiguration();
            }
        });
    }

    @Override
    public void run(CommerceConfiguration configuration, Environment environment) {
        final ProductDAO dao = new ProductDAO(hibernateBundle.getSessionFactory());
        final OrderDAO orderDao = new OrderDAO(hibernateBundle.getSessionFactory());
        final ResourceMapppingDAO resourceMapppingDAO = new ResourceMapppingDAO(hibernateBundle.getSessionFactory());

        ProductResource pr = new ProductResource(dao);
        OrderResource or = new OrderResource(orderDao);
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(pr);
        environment.jersey().register(or);
        environment.jersey().register(new GenericResource(resourceMapppingDAO, pr, or));
    }
}
