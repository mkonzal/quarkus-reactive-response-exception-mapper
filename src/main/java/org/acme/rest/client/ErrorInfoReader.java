package org.acme.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Priority;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
@Consumes("text/plain")
@Priority(0)
public class ErrorInfoReader implements MessageBodyReader<String> {
    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations,
            final MediaType mediaType) {
        final boolean isReadable = type == String.class;
        Logger.getLogger(ErrorInfoReader.class).info("Message body is " + (isReadable ? "" : "not ") + "readable");
        return isReadable;
    }

    @Override
    public String readFrom(final Class<String> type, final Type genericType, final Annotation[] annotations,
            final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream)
            throws IOException {
        final String content = new BufferedReader(new InputStreamReader(entityStream)).readLine();
        Logger.getLogger(ErrorInfoReader.class)
                .info("Content-length " + httpHeaders.getFirst("content-length") + " content " + content);
        return content;
    }
}
