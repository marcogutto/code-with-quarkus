package org.acme.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.acme.domain.dto.PeopleResultDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey = "swapi")
public interface SWAPIService {

    @GET
    @Path(value = "/people/")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PeopleResultDTO> searchPeople(@QueryParam(value = "search") String search);

    // @GET
    // @Path(value = "/people/{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Uni<PeopleResultDTO> findPeopleById(@PathParam(value = "id") String id);
    
}