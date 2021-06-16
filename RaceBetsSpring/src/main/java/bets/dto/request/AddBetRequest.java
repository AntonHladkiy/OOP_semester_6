package bets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBetRequest {
    private Double additionalBet;
    private Double currentBet;
    private Long race_id;
}
