package bets.repo;

import bets.entity.Bet;
import bets.entity.Race;
import bets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BetsRepo extends JpaRepository<Bet, Long> {
    Optional<Bet> findByRaceAndUser(Race race, User user);
    List<Bet> findALLByUser(User user);
    List<Bet> findALLByRace(Race race);
    List<Bet> findAll();
}
