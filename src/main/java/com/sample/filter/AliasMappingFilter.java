package com.sample.filter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class AliasMappingFilter implements ContainerRequestFilter {

  public static final String ALIAS_TENANT = "alias";
  public static final Long ALIAS_USER_ID = Long.valueOf(9999999L);
  private static final String USERS = "users";
  private static final String TENANTS = "tenants";

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    UriInfo uriInfo = requestContext.getUriInfo();
    URI requestUri = uriInfo.getRequestUri();

    String urlPath = requestUri.getPath();
    urlPath = replaceWithAlias(urlPath);
    try {
      requestContext.setRequestUri(new URI(urlPath));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private String replaceWithAlias(String url) {
    String[] segments = url.split("/");
    int indexOfUsers = -1;
    int indexOfTenants = -1;
    for (int i = 0; i < segments.length; i++) {
      if (segments[i].equals(USERS)) {
        indexOfUsers = i + 1;
      }
      if (segments[i].equals(TENANTS)) {
        indexOfTenants = i + 1;
      }
    }

    if (indexOfTenants > 0 && indexOfTenants < segments.length) {
      segments[indexOfTenants] = ALIAS_TENANT;
    }
    if (indexOfUsers > 0 && indexOfUsers < segments.length) {
      segments[indexOfUsers] = ALIAS_USER_ID.toString();
    }
    return buildUrl(segments);
  }

  private String buildUrl(String[] segments) {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < segments.length; i++) {
      if (i == 0) {
        buf.append(segments[i]);
      } else {
        buf.append("/").append(segments[i]);
      }
    }
    return buf.toString();
  }

}
