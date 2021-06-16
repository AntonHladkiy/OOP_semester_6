package bets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BetRequest {
    private Double bet;
    private Long race_id;
    private Long runner_id;
}
