package entity.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RacesAdminResponse {
    private List<RaceResponseAvailable> races;
}
