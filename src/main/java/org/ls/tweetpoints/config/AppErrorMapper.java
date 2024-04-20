package org.ls.tweetpoints.config;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AppErrorMapper implements ExceptionMapper<AppException> {

    @Override
    public Response toResponse(AppException exception) {
        return Response.status(exception.getError()).entity(new AppError(exception.getError(), exception.getMessage())).build();
    }
    
}
