package bets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinishedRacesUserResponse {
    private Long race_id;
    private String raceName;
    private Double bet;
    private Double win;
}
