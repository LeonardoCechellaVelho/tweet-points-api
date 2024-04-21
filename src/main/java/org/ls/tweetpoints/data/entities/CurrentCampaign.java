package org.ls.tweetpoints.data.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CurrentCampaign {
    private Campaign campaign;
    private LocalDateTime timestamp;
}
