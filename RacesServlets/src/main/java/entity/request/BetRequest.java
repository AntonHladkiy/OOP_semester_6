package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BetRequest {
    private String token;
    private Double bet;
    private Integer race_id;
    private Integer runner_id;
}
