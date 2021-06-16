package entity.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RacesUserResponse {
    private List<RaceResponseFinished> finishedRaces;
    private List<RaceResponseAvailable> availableRaces;
    private List<RaceResponseBetted> bettedRaces;
}