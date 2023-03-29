package test.openapi.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.camel.ProducerTemplate;


@ApplicationScoped
@Path("/my-app")
public class AppResource {

    @Inject
    ProducerTemplate producerTemplate;

    @GET
    public String getAllFruits(){
        return producerTemplate.requestBody("direct:get-fruits", null,
                String.class);

    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public String addFruit(String fruit){
        return  producerTemplate.requestBody("direct:add-fruit", fruit, String.class);
    }

}
