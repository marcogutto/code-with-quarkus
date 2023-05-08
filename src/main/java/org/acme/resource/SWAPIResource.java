package org.acme.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.domain.entity.PeopleDTO;
import org.acme.domain.entity.PlanetDTO;
import org.acme.service.SWAPIService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/v1/swapi")
public class SWAPIResource {

    @Inject
    @RestClient
    SWAPIService service;

    @GET
    @Path(value = "/people/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PeopleDTO getPeopleById(@PathParam(value = "id") String id){

        return service.getPeopleById(id);

    }

    @GET
    @Path(value = "/people/{id}/homeworld")
    @Produces(MediaType.APPLICATION_JSON)
    public PlanetDTO getHomeWorldByPeopleId(@PathParam(value = "id") String id){

        PeopleDTO people = service.getPeopleById(id);

        String planetId = String.valueOf(people.getHomeworld().charAt(people.getHomeworld().length()-2));

        return service.getPlanetdById(planetId);

    }
}