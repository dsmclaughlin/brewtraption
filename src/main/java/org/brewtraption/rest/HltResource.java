package org.brewtraption.rest;

import org.brewtraption.control.BreweryController;
import org.brewtraption.control.OverrideState;
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
  }

  @GET
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getHLTInfo() {
    HltDTO info = BreweryController.getHTLInfo();
    return Response.ok(info).build();
  }

  @PUT
  @Path("/target")
  @Consumes({ MediaType.APPLICATION_JSON })
  public Response setTemperature(final Double target) {
    BreweryController.setHLTTargetTemperature(target);
    return  Response.ok().build();
  }

  @PUT
  @Path("/override")
  @Consumes({ MediaType.APPLICATION_JSON })
  public Response setHeaterOnOff(final OverrideState state) {
    BreweryController.overrideHeater(state);
    return  Response.ok().build();
  }
}