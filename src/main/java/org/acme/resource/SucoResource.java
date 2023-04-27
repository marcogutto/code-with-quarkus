package org.acme.resource;

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
import org.bson.types.ObjectId;

import io.smallrye.mutiny.Uni;

@Path("/v1/sucos")
public class SucoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findAll() {

        return Suco.findAll().list().onItem().transform(s -> {
            return Response.status(Response.Status.OK).entity(s).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findById(@PathParam("id") String id) {

        return Suco.findById(new ObjectId(id)).onItem().transform(s -> {
            return Response.status(Response.Status.OK).entity(s).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> save(Suco suco) {

        return suco.persist().onItem().transform(s -> {
            return Response.status(Response.Status.CREATED).entity(s).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> update(@PathParam("id") String id, Suco suco) {

        Uni<Suco> sucoUpdate = Suco.findById(new ObjectId(id));

        return sucoUpdate.onItem().transform(s -> {
            s.setNome(suco.getNome());
            s.setDescricao(suco.getDescricao());
            s.setValor(suco.getValor());

            s.update().subscribe().asCompletionStage();

            return s;
        }).onItem().transform(s -> {
            return Response.status(Response.Status.OK).entity(s).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());            
 
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> partialUpdate(@PathParam("id") String id, SucoParcial sucoParcial) {

        Uni<Suco> sucoUpdate = Suco.findById(new ObjectId(id));

        return sucoUpdate.onItem().transform(s -> {
            s.setDescricao(sucoParcial.getDescricao());
            s.setValor(sucoParcial.getValor());

            s.update().subscribe().asCompletionStage();

            return s;
        }).onItem().transform(s -> {
            return Response.status(Response.Status.OK).entity(s).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> remove(@PathParam("id") String id) {

        return Suco.findById(new ObjectId(id)).onItem().transform(s -> {
            s.delete().subscribe().asCompletionStage();
            return Response.status(Response.Status.OK).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}