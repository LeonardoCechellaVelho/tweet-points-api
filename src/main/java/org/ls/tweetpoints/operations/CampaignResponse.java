package org.ls.tweetpoints.operations;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CampaignResponse {
    
    private boolean sucesso;
    private UUID id;
}
