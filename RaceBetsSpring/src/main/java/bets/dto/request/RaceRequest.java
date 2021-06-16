package bets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceRequest {
    private Long runner_id1;
    private Long runner_id2;
    private Double coef1;
    private Double coef2;
    private String name;
}
