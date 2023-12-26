package org.acme.resource;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.domain.dto.PeopleDTO;
import org.acme.domain.entity.People;
import org.acme.domain.mapper.PeopleMapper;
import org.acme.service.SWAPIService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

@Path("/v1/people")
public class PeopleResource {

    @Inject
    PeopleMapper mapper;

    @Inject
    @RestClient
    SWAPIService service;

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Uni<Response> findAll() {

    //     return People.findAll().list().onItem().transform(s -> {
    //         return Response.status(Response.Status.OK).entity(s).build();
    //     }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    // }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findByName(@QueryParam("search") String search) {

        String pattern = "^"+search;

        if(search == null || search.length() == 0){

            return People.findAll().list().onItem().transform(s -> {
                return Response.status(Response.Status.OK).entity(s).build();
            }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());

        }

        return People.find("{ name: { $regex: ?1, $options: 'i' } } ", search).firstResult()
        .onItem().ifNotNull()
            .transform(p -> {
                return Response.status(Response.Status.OK).entity(mapper.toDTO((People) p)).build();
            })
        .onItem().ifNull().switchTo(service.searchPeople(search)
            .onItem().transform(p -> {
                if(p.getResults() != null && p.getResults().size() > 0){
                    return Response.status(Response.Status.OK).entity(p.getResults().get(0)).build();    
                }
                return Response.status(Response.Status.OK).build();
            }))
        .onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> save(PeopleDTO people) {

        // String pattern = "^"+people.getName();

        return People.find("{ name: { $regex: ?1 , $options: 'i' } }", people.getName()).firstResult()
        .onItem().ifNotNull()
            .transform(p -> {
                return Response.status(Response.Status.CREATED).entity(p).build();
            })
        .onItem().ifNull().switchTo(service.searchPeople(people.getName())
            .onItem().transform(p -> {
                if(p.getResults() != null && p.getResults().size() > 0){
                    People.persist(mapper.toEntity(p.getResults().get(0))).subscribeAsCompletionStage();
                    return Response.status(Response.Status.CREATED).entity(p.getResults().get(0)).build();
                }
                return Response.status(Response.Status.CREATED).build();
            }))
        .onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> remove(@PathParam("id") String id) {

        return People.findById(new ObjectId(id)).onItem().transform(s -> {
            s.delete().subscribe().asCompletionStage();
            return Response.status(Response.Status.OK).build();
        }).onFailure().recoverWithItem(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}