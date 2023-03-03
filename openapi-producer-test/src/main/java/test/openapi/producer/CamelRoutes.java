package test.openapi.producer;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rest.openapi.RestOpenApiComponent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    CamelContext context;

    @ConfigProperty(name = "open.api.provider.host", defaultValue = "http://localhost:8081")
    String openApiProviderHost;

    @Named("rest-openapi")
    @Singleton
    public Component petstore() throws URISyntaxException {
        RestOpenApiComponent fruitStore = new RestOpenApiComponent(context);
        final var specificationHost = String.format("%s/q/openapi?format=YAML", openApiProviderHost);
        fruitStore.setSpecificationUri(new URI(specificationHost));
        fruitStore.setHost(openApiProviderHost);
        return fruitStore;
    }

    @Override
    public void configure() throws Exception {

        from("direct:get-fruits")
                .to("rest-openapi:#get-fruits");

        from("direct:add-fruit")
                .to("rest-openapi:#post-fruits")

        ;

    }
}
