package org.ls.tweetpoints.operations.user;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.ls.tweetpoints.data.entities.User;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface UserOperation {
    @POST
    @Operation(summary = "Add User", description = "Add User")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "User added!", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = UserResponse.class))),
        @APIResponse(responseCode = "400", description = "Bad Request", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = String.class))),
        @APIResponse(responseCode = "422", description = "Unprocessable content", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = String.class))),
        @APIResponse(responseCode = "500", description = "System Error", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = String.class)))
    })
    UserResponse addUser(@RequestBody(name = "Add User", description = "User data") User user);
}
