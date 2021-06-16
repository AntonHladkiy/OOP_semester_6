package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBetRequest {
    private String token;
    private Double additionalBet;
    private Double currentBet;
    private Integer race_id;
}
