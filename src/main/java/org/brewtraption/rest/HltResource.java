package org.brewtraption.rest;

import org.brewtraption.dao.BreweryDAO;
import org.brewtraption.dto.HltDTO;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Singleton
@Path("hlt")
public class HltResource {

  @Context
  UriInfo uriInfo;

  public HltResource() {
    System.out.println("TestResource constructed");
  }

  @GET
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getHLTInfo() {
    HltDTO info = BreweryDAO.getHTLInfo();
    return Response.ok(info).build();
  }

  @PUT
  @Consumes({ MediaType.APPLICATION_JSON })
  @Path("/temp")
  public Response setTemperature(final HltDTO hltDTO) {
    BreweryDAO.setHLTTargetTemperature(hltDTO);
    return  Response.accepted().build();
  }
}