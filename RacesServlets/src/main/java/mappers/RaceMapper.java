package mappers;

import entity.Runner;
import entity.response.RaceResponseAvailable;
import entity.response.RaceResponseBetted;
import entity.response.RaceResponseFinished;
import repo.RunnerRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RaceMapper {
    public static final RaceMapper INSTANCE = new RaceMapper();

    public RaceResponseAvailable resultSetToResponse(ResultSet resultSet) throws SQLException {
        RaceResponseAvailable race = new RaceResponseAvailable();
        Runner runner1 ;
        Runner runner2 ;
        Optional<Runner> optionalRunner1 = RunnerRepo.INSTANCE.findById(resultSet.getInt("runner_id1") );
        if (optionalRunner1.isPresent()) {
            runner1= optionalRunner1.get();
        } else throw new RuntimeException("e");
        Optional<Runner> optionalRunner2 = RunnerRepo.INSTANCE.findById(resultSet.getInt("runner_id2") );
        if (optionalRunner2.isPresent()) {
            runner2= optionalRunner2.get();
        } else throw new RuntimeException("e");
        race.setRace_id( resultSet.getInt("id") );
        race.setRaceName( resultSet.getString( "name" ) );
        race.setCoef1( resultSet.getDouble( "coef1" ) );
        race.setCoef2( resultSet.getDouble( "coef2" ) );
        race.setRunner1( runner1);
        race.setRunner2( runner2);
        return race;
    }
    public RaceResponseFinished resultSetToResponseFinished(ResultSet resultSet) throws SQLException {
        RaceResponseFinished race = new RaceResponseFinished();
        race.setRace_id( resultSet.getInt("id") );
        race.setRaceName( resultSet.getString( "name" ) );
        race.setBet( resultSet.getDouble( "bet") );
        race.setWin( resultSet.getDouble( "win") );
        return race;
    }

    public RaceResponseBetted resultSetToResponseWithBet(ResultSet resultSet) throws SQLException{
        Runner runner ;
        Optional<Runner> optionalRunner = RunnerRepo.INSTANCE.findById(resultSet.getInt("runner_id") );
        if (optionalRunner.isPresent()) {
            runner= optionalRunner.get();
        } else throw new RuntimeException("e");
        RaceResponseBetted race=new RaceResponseBetted();
        race.setBet( resultSet.getDouble( "bet" ) );
        race.setRace_id( resultSet.getInt("id") );
        race.setRaceName( resultSet.getString( "name" ) );
        race.setRunner( runner );
        if(runner.getId()==resultSet.getInt("runner_id")){
            race.setCoef( resultSet.getDouble( "coef1" ) );
        } else{
            race.setCoef( resultSet.getDouble( "coef2" ) );
        }
        return race;
    }
}
