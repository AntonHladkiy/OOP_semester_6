package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceRequest {
    private Integer runner_id1;
    private Integer runner_id2;
    private Double coef1;
    private Double coef2;
    private String token;
    private String name;
}
