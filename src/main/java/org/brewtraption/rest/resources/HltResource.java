package org.brewtraption.rest.resources;

import com.codahale.metrics.annotation.Timed;
import org.brewtraption.controller.UserController;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Singleton
@Path("hlt")
public class HltResource {

  @Context
  UriInfo uriInfo;

  UserController userController = new UserController();

  public HltResource() {
    System.out.println("TestResource constructed");
  }

  @Timed
  @GET
  @Produces("text/plain")
  public String testResource() {
    return "HTL Response";
  }

}