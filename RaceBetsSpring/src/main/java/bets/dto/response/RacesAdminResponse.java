package bets.dto.response;

import bets.entity.Race;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RacesAdminResponse {
    private List<Race> races;
}
