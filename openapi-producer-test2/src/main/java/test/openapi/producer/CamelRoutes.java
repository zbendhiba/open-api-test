package test.openapi.producer;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.camel.Component;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.quarkus.rest.openapi.ValidationHandler;
import org.apache.camel.quarkus.component.openapi.validation.RestOpenApiValidationHandler;






@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    /*@Named("rest-openapi")
    @Singleton
    public Component petstore() throws URISyntaxException {
        RestOpenApiComponent fruitStore = new RestOpenApiComponent(context);
        final var specificationHost = String.format("%s/q/openapi?format=YAML", openApiProviderHost);
        fruitStore.setSpecificationUri(new URI(specificationHost));
        fruitStore.setHost(openApiProviderHost);
        return fruitStore;
    }*/

    @Override
    public void configure() throws Exception {
        RestOpenApiValidationHandler handler;
        restConfiguration()
                .apiProperty("handler.validationEnabled", "true")
                .apiProperty("handler.validationSchemaCacheEnabled", "true")
                .apiProperty("handler.validationSpecificationUri", "classpath:/my-openapi-spec.yaml")
                .apiProperty("handler.validationSchemaCacheSpecifications", "classpath:/my-openapi-spec.yaml")
                .apiProperty("handler.validationSpecificationUrl", "classpath:/my-openapi-spec.yaml")
                .end();

       /* from("direct:createUser")
                .to("openapi-java-dsl:classpath:/my-openapi-spec.yaml")
                .log("Request is valid according to OpenAPI spec")

                .log("User created successfully");*/


        from("direct:add-fruit")
                //from("direct:createUser")
                .doTry()
                    //.setProperty(OpenApi3Constants.OPENAPI_OPERATION_ID, constant("createUser"))
                    .toD("openapi-java:my-openapi-spec.yaml?apiContextId=myapi")
                    .log("Request is valid according to OpenAPI spec")
                    //.to("openapi-java:/home/zbendhib/dev/open-api-test/openapi-producer-test2/src/main/resources/my-openapi-spec.yaml")
                    .log("Request is valid according to OpenAPI spec")
                    .setHeader("CamelHttpResponseCode", constant(200))

                .doCatch(Exception.class)
                    .log("Error handling the request: ${exception.message}")
                    .setHeader("CamelHttpResponseCode", constant(400))
                    .setBody(constant("Bad Request"))
                .endDoTry();


    }
}
