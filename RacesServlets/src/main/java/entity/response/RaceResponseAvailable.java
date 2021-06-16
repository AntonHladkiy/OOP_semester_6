package entity.response;

import entity.Runner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceResponseAvailable {
    private Integer race_id;
    private String raceName;
    private Double coef1;
    private Double coef2;
    private Runner runner1;
    private Runner runner2;
}
