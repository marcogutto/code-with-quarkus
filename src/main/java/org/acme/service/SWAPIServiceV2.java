package org.acme.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.domain.entity.PeopleDTO;
import org.acme.domain.entity.PlanetDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey = "swapi")
public interface SWAPIServiceV2 {

    @GET
    @Path(value = "/people/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PeopleDTO> getPeopleById(@PathParam(value = "id") String id);

    @GET
    @Path(value = "/planets/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PlanetDTO> getPlanetdById(@PathParam(value = "id") String id);
    
}
