package org.acme.rest.client;

import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@Path("remote-service")
@RegisterRestClient(configKey = "test-api")
@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
@RegisterProvider(CustomResponseExceptionMapper.class)
public interface ClientService {
    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    Uni<Response> connect();

    default String lookupAuth() {
        return "Basic " + Base64.getEncoder().encodeToString("usr:pwda".getBytes());
    }
}
