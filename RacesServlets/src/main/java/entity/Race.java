package entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Race {
    private Integer id;
    private Integer runner_id1;
    private Integer runner_id2;
    private Double coef1;
    private Double coef2;
    private String name;
}