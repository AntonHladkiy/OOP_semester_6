package bets.repo;

import bets.entity.BookmakerData;
import bets.entity.Race;
import bets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmakerDataRepo extends JpaRepository<BookmakerData, Long> {
    List<BookmakerData> findAllByDateAndBookmaker(java.sql.Date date, User bookmaker);
    List<BookmakerData> findAllByBookmaker(User bookmaker);
}
