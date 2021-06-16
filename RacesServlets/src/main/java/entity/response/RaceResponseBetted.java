package entity.response;

import entity.Runner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceResponseBetted {
    private Integer race_id;
    private String raceName;
    private Double coef;
    private Runner runner;
    private Double bet;
}