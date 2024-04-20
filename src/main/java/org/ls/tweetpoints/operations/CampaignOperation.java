package org.ls.tweetpoints.operations;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface CampaignOperation {
    @POST
    @Operation(summary = "Add Campaign", description = "Add Campaign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Campaign added!", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CampaignResponse.class)))
    })
    CampaignResponse addCampaign(@RequestBody(name = "Add Campaign", description = "Campaign data") CampaignModel campaign);
}
