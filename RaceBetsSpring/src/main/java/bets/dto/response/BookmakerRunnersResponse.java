package bets.dto.response;

import bets.entity.Runner;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookmakerRunnersResponse {
    private List<Runner> runners;
}
