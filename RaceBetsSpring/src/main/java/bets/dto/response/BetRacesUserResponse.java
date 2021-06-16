package bets.dto.response;

import bets.entity.Runner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BetRacesUserResponse {
    private Long race_id;
    private String raceName;
    private Double coef;
    private Runner runner;
    private Double bet;
}
