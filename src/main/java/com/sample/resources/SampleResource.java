package com.sample.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.sample.filter.AliasMappingFilter;

@Path("/api/tenants/{tenant}/users")
@Produces("text/plain")
public class SampleResource {

  @Autowired
  private SampleRepository sampleRepository;

  /**
   * Only return payload when alias is involved.
   * 
   * @param tenant
   * @return
   */
  @GET
  public String getUsers(@PathParam("tenant") String tenant) {
    if (tenant.equals(AliasMappingFilter.ALIAS_TENANT)) {
      return sampleRepository.getUsers().toString();
    } else {
      return "";
    }
  }

  /**
   * Only return payload when alias is involved.
   * 
   * @param tenant
   * @param id
   * @return
   */
  @GET
  @Path("{id}")
  public String getUser(@PathParam("tenant") String tenant, @PathParam("id") Long id) {
    if (tenant.equals(AliasMappingFilter.ALIAS_TENANT)
        && id.equals(AliasMappingFilter.ALIAS_USER_ID)) {
      return sampleRepository.getUser(id).toString();
    } else {
      return "";
    }
  }

}
