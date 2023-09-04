package org.acme.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;

@Path("/protected")
public class ProtectedResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        System.out.println("Inicio ...");

        Uni<String> resposta = Uni.createFrom().item("You are protected ...").onItem().transform(t -> {

            try {
                Thread.sleep(1000);

                System.out.println("Processado ...");

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return t;

        });

        System.out.println("Entregue ...");

        return resposta;
    }
}