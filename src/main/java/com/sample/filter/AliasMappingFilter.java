package com.sample.filter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class AliasMappingFilter implements ContainerRequestFilter {

  public static final String ALIAS_NAME = "alias";
  private Map<String, String> aliasMapping = new HashMap<>();

  @PostConstruct
  public void init() {
    // supposed to load from somewhere else
    aliasMapping.put("tenant", ALIAS_NAME);
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    UriInfo uriInfo = requestContext.getUriInfo();
    URI requestUri = uriInfo.getRequestUri();
    String urlPath = requestUri.getPath();
    for (Map.Entry<String, String> entry : aliasMapping.entrySet()) {
      urlPath = urlPath.replaceFirst("/" + entry.getKey(), "/" + entry.getValue());
    }
    try {
      requestContext.setRequestUri(new URI(urlPath));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

}
