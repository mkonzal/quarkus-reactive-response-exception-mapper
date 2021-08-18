package org.acme.rest.client;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;

public class CustomResponseExceptionMapper implements ResponseExceptionMapper<MyException> {
    @Override
    public boolean handles(final int status, final MultivaluedMap<String, Object> headers) {
        final boolean toHandle = status != 201;
        Logger.getLogger(CustomResponseExceptionMapper.class)
                .info("Message is " + (toHandle ? "" : "not ") + "to handle");
        return toHandle;
    }

    @Override
    public MyException toThrowable(final Response response) {
        Logger.getLogger(CustomResponseExceptionMapper.class).info("Create throwable!");
        return new MyException(response.getStatus(), response.readEntity(String.class));
    }
}
