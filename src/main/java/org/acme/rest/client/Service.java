package org.acme.rest.client;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import io.smallrye.mutiny.Uni;

@Path("/service")
public class Service {
    @RestClient
    ClientService clientService;

    @ServerExceptionMapper
    public Response mapException(final MyException x) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity("status code: " + x.getStatus() + "error info: " + x.getErrorInfo())
                .build();
    }

    @GET
    @Path("/connect")
    public Uni<String> connect() {
        return clientService.connect()
                // .onFailure()
                // .transform(Throwable::getCause)
                .onItem()
                .transform(r -> getConId(r.getLocation()));
    }

    private static String getConId(final URI uri) {
        final String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
