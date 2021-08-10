package org.acme.rest.client;

import javax.annotation.Priority;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;

@Provider
@Priority(0)
public class CustomResponseExceptionMapper implements ResponseExceptionMapper<MyException> {
    @Override
    public boolean handles(final int status, final MultivaluedMap<String, Object> headers) {
        Logger.getLogger(CustomResponseExceptionMapper.class).info("Handle is " + (status != 201));
        return status != 201;
    }

    @Override
    public MyException toThrowable(final Response response) {
        Logger.getLogger(CustomResponseExceptionMapper.class).info("Create throwable!");
        return new MyException(response.getStatus(), response.readEntity(String.class));
    }
}
