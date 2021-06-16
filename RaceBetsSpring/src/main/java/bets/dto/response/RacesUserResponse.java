package bets.dto.response;

import bets.entity.Race;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RacesUserResponse {
    private List<FinishedRacesUserResponse> finishedRaces;
    private List<Race> availableRaces;
    private List<BetRacesUserResponse> bettedRaces;
}
