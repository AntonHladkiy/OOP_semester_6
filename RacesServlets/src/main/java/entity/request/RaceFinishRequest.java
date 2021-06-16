package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceFinishRequest {
    private Integer race_id;
    private Integer winner_id;
    private Double winner_coef;
    private String token;
}