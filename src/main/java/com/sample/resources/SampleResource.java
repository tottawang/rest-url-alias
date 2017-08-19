package com.sample.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

@Path("/api/users")
@Produces("text/plain")
public class SampleResource {

  @Autowired
  private SampleRepository sampleRepository;

  @GET
  public String getUsers() {
    return sampleRepository.getUsers().toString();
  }

  @GET
  @Path("{id}")
  public String getUser(@PathParam("id") Long id) {
    return sampleRepository.getUser(id).toString();
  }

}
