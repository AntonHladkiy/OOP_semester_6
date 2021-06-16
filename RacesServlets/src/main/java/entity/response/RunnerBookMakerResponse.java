package entity.response;

import entity.Runner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RunnerBookMakerResponse {
    private List<Runner> runners;
}
