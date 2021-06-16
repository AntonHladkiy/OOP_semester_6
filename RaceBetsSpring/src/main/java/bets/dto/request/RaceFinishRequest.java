package bets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceFinishRequest {
    private Long race_id;
    private Long winner_id;
    private Double winner_coef;
}
