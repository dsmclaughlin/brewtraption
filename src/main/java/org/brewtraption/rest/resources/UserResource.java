package org.brewtraption.rest.resources;

import com.codahale.metrics.annotation.Timed;
import org.brewtraption.controller.UserController;
import org.brewtraption.controller.exception.UserException;
import org.brewtraption.dto.UserDTO;

import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Singleton
@Path("user")
public class UserResource {

  @Context
  UriInfo uriInfo;

  UserController userController = new UserController();

  public UserResource() {
  }

  @Timed
  @GET
  @Path("{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUser(@PathParam("username") final String userName) {
    try {
      UserDTO user = userController.getUser(userName);
      return Response.ok().entity(user).build();
    } catch (UserException e) {
      return Response.status(Status.NOT_FOUND).entity(e).build();
    }
  }

  @Timed
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(final UserDTO user) {
    try {
      userController.createUser(user);
      UriBuilder ub = uriInfo.getAbsolutePathBuilder();
      URI uri = ub.path(user.getUserName()).build();
      return Response.created(uri).build();
    } catch (UserException e) {
      return Response.status(Status.BAD_REQUEST).entity(e).build();
    }
  }

}