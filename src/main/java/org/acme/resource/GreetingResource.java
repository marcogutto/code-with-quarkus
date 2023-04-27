package org.acme.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path(value = "/mutiny")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> helloMutiny() {
        return Uni.createFrom().item("Hello from RESTEasy Reactive").onItem().transform(h -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return h;
        }).onFailure().recoverWithNull();
    }
}