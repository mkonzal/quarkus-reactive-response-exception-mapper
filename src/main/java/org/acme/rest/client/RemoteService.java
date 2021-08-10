package org.acme.rest.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestHeader;

import io.smallrye.mutiny.Uni;

@Path("/remote-service")
public class RemoteService {
    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public Uni<Response> connect(@RestHeader("Authorization") final String authorization, final UriInfo uriInfo) {
        Logger.getLogger(getClass()).info("Remote service call");
        if ("usr:pwd".equals(new String(Base64.getDecoder().decode(authorization.substring("Basic".length()).trim()),
                StandardCharsets.UTF_8))) {
            return Uni.createFrom()
                    .item(Response.status(Response.Status.CREATED)
                            .location(
                                    UriBuilder.fromPath(uriInfo.getAbsolutePath() + "/" + UUID.randomUUID().toString())
                                            .build())
                            .build());
        }

        return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).entity("Test error message").build());
    }
}
