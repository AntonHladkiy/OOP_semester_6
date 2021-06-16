package mappers;

import entity.Bet;
import entity.Runner;
import repo.RunnerRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BetMapper {
    public static final BetMapper INSTANCE = new BetMapper();
    public Bet resultSetToEntity(ResultSet resultSet) throws SQLException {
        Bet bet = new Bet();
        bet.setBet( resultSet.getDouble( "bet" ) );
        bet.setRace_id( resultSet.getInt( "race_id" ) );
        bet.setRunner_id( resultSet.getInt( "runner_id" ) );
        bet.setUser_id( resultSet.getInt( "user_id" ) );
        bet.setWin( resultSet.getDouble( "win" ) );
        return bet;
    }
}
