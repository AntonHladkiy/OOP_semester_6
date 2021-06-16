package bets.repo;

import bets.entity.Bet;
import bets.entity.Race;
import bets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RacesRepo extends JpaRepository<Race, Long> {
    List<Race> findAllByFinished(Boolean finished);
    List<Race> findAllByBookmaker(User bookmaker);
}
