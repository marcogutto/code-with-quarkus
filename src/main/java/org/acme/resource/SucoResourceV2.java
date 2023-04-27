package org.acme.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Uni;

@Path("/v2/sucos")
public class SucoResourceV2 {

    List<Suco> sucos = new ArrayList<Suco>();

    SucoResourceV2(){
        sucos.add(new Suco("1", "Uva", "Suco Natural de Uva", new BigDecimal("8.81")));
        sucos.add(new Suco("2", "Laranja", "Suco Natural de Laranja", new BigDecimal("9.56")));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findAll() {

        ObjectMapper mapper = new ObjectMapper();

        try {

            Thread.sleep(10000);

            return Uni.createFrom().item(Response.status(Response.Status.OK).entity(mapper.writeValueAsString(sucos)).build());
        } catch(Exception e){
            return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findById(@PathParam("id") String id) {

        ObjectMapper mapper = new ObjectMapper();

        Suco suco = sucos.stream()
                        .filter(s -> s.getId().equals(id))
                        .findAny()
                        .orElse(null);

        try {

            Thread.sleep(10000);

            if(suco != null){
                return Uni.createFrom().item(Response.status(Response.Status.OK).entity(mapper.writeValueAsString(suco)).build());
            }
            return Uni.createFrom().item(Response.status(Response.Status.NO_CONTENT).build());
        } catch(Exception e){
            return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> save(Suco suco) {

        ObjectMapper mapper = new ObjectMapper();

        Suco ultimo = sucos.get(sucos.size()-1);

        suco.setId(String.valueOf(Integer.valueOf(ultimo.getId())+1));

        sucos.add(suco);

        try {
            return Uni.createFrom().item(Response.status(Response.Status.OK).entity(mapper.writeValueAsString(suco)).build());
        } catch(Exception e){
            return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> update(@PathParam("id") String id, Suco suco) {

        ObjectMapper mapper = new ObjectMapper();

        Suco sucoUpdate = sucos.stream()
                        .filter(s -> s.getId().equals(id))
                        .findAny()
                        .orElse(null);
        
        int index = sucos.indexOf(sucoUpdate);

        sucos.set(index, suco);

        try {
            return Uni.createFrom().item(Response.status(Response.Status.OK).entity(mapper.writeValueAsString(suco)).build());
        } catch(Exception e){
            return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> partialUpdate(@PathParam("id") String id, SucoParcial sucoParcial) {

        ObjectMapper mapper = new ObjectMapper();

        Suco sucoUpdate = sucos.stream()
                        .filter(s -> s.getId().equals(id))
                        .findAny()
                        .orElse(null);

        try {
            if(sucoUpdate == null){
                return Uni.createFrom().item(Response.status(Response.Status.NO_CONTENT).build());
            } else {
                int index = sucos.indexOf(sucoUpdate);

                sucoUpdate.setValor(sucoParcial.getValor());
                sucoUpdate.setDescricao(sucoParcial.getDescricao());
        
                sucos.set(index, sucoUpdate);
                return Uni.createFrom().item(Response.status(Response.Status.OK).entity(mapper.writeValueAsString(sucoUpdate)).build());
            }
        } catch(Exception e){
            return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> remove(@PathParam("id") String id) {

        Suco sucoDelete = sucos.stream()
                        .filter(s -> s.getId().equals(id))
                        .findAny()
                        .orElse(null);
        
        int index = sucos.indexOf(sucoDelete);

        sucos.remove(index);

        return Uni.createFrom().item(Response.status(Response.Status.NO_CONTENT).build());
    }
}