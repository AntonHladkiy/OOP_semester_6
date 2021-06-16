package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bet {
    private Integer race_id;
    private Integer user_id;
    private Double bet;
    private Integer runner_id;
    private Double win;
}
