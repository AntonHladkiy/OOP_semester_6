package bets.repo;

import bets.entity.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunnersRepo extends JpaRepository<Runner, Long> {
    List<Runner> findAll();
}