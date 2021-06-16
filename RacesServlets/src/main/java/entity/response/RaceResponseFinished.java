package entity.response;

import entity.Runner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceResponseFinished {
    private Integer race_id;
    private String raceName;
    private Double bet;
    private Double win;
}