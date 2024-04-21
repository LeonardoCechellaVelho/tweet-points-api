package org.ls.tweetpoints.operations.campaign;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.NewCampaignModel;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface CampaignOperation {
    @POST
    @Path("/add")
    @Operation(summary = "Add Campaign", description = "Add Campaign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Campaign added!", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = CampaignResponse.class))),
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
    CampaignResponse addCampaign(@RequestBody(name = "Add Campaign", description = "Campaign data") CampaignModel campaign);

    @POST
    @Path("/set")
    @Operation(summary = "Set Current Campaign", description = "Set Current Campaign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Campaign set!", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = CampaignResponse.class))),
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
    CampaignResponse setCurrentCampaign(@RequestBody(name = "Set Current Campaign", description = "Campaign data") CampaignModel campaign);

    @PUT
    @Path("/update")
    @Operation(summary = "Update Campaign", description = "Update Campaign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Campaign updated!", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = CampaignResponse.class))),
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
    CampaignResponse updateCampaign(@RequestBody(name = "Update Campaign", description = "Campaign data") NewCampaignModel request);
    
    @GET
    @Path("/get")
    @Operation(summary = "Get Current Campaign", description = "Get Current Campaign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Campaign found!", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                     schema = @Schema(implementation = CampaignResponse.class))),
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
    CampaignResponse getCurrentCampaign();
}
