package org.acme.resource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.domain.entity.Suco;
import org.acme.domain.entity.SucoParcial;

import io.smallrye.mutiny.Uni;

@Path("/v2/sucos")
public class SucoResourceV2 {

    @Inject
    SucoResource resource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findAll() {
        // System.out.println("Antes ...\n");

        Uni<Response> response = Uni.createFrom().item(resource.findAll()).onItem().transform(s -> {
            // System.out.println("Suco ... \n");
            return s;
        });

        // System.out.println("Depois ... \n");

        return response;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findById(@PathParam("id") String id) {
        return Uni.createFrom().item(resource.findById(id));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> save(Suco suco) {
        return Uni.createFrom().item(resource.save(suco));
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> update(@PathParam("id") String id, Suco suco) {
        return Uni.createFrom().item(resource.update(id, suco));
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> partialUpdate(@PathParam("id") String id, SucoParcial sucoParcial) {
        return Uni.createFrom().item(resource.partialUpdate(id, sucoParcial));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> remove(@PathParam("id") String id) {
        return Uni.createFrom().item(resource.remove(id));
    }
}