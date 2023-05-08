package org.acme.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.domain.entity.PeopleDTO;
import org.acme.domain.entity.PlanetDTO;
import org.acme.service.SWAPIServiceV2;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

@Path("/v2/swapi")
public class SWAPIResourceV2 {

    @Inject
    @RestClient
    SWAPIServiceV2 service;

    @GET
    @Path(value = "/people/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PeopleDTO> getPeopleById(@PathParam(value = "id") String id){

        return service.getPeopleById(id).onFailure().recoverWithNull();

    }

    @GET
    @Path(value = "/people/{id}/homeworld")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PlanetDTO> getHomeWorldByPeopleId(@PathParam(value = "id") String id){

        return service.getPeopleById(id)
            .onItem().transform(p -> {
                return String.valueOf(p.getHomeworld().charAt(p.getHomeworld().length()-2));
            }).onItem().transformToUni(planetId -> {
                return service.getPlanetdById(planetId);
            }).onFailure().recoverWithNull();
            
    }
}