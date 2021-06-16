package bets.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmakerIncomeStats {
    private List<BookmakerIncomeStatForDay> bookmakerStats;
    private String name;
}
