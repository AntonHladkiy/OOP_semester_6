package bets.dto.response;

import bets.entity.Runner;
import bets.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmakerStatsResponse {
    private Runner runner;
    private User better;
    private Integer runnerWins;
    private Double betterWins;
}
