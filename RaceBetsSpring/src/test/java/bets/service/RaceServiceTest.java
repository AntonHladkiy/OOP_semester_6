package bets.service;

import bets.dto.UserDTO;
import bets.dto.request.RaceFinishRequest;
import bets.dto.request.RaceRequest;
import bets.dto.response.BookmakerRunnersResponse;
import bets.dto.response.RacesAdminResponse;
import bets.dto.response.RacesUserResponse;
import bets.entity.Bet;
import bets.entity.Race;
import bets.entity.Runner;
import bets.entity.User;
import bets.repo.BetsRepo;
import bets.repo.RacesRepo;
import bets.repo.RunnersRepo;
import bets.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.naming.Name;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RaceServiceTest {
    private static final Long USERID = 1L;
    private static final Long BETID = 1L;
    private static final Long RUNNER_1ID = 1L;
    private static final Long RUNNER_2ID = 2L;
    private static final Long RACE_ID = 1L;
    private static final Double COEF_1 = 1D;
    private static final Double COEF_2 = 1D;
    private static final String NAME = "name";
    private static final String RACE1 = "race1";

    @InjectMocks
    private RaceService raceService;


    @Mock
    private RacesRepo racesRepo;

    @Mock
    private BetsRepo betsRepo;

    @Mock
    private RunnersRepo runnersRepo;
    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Test
    public void addRace( ) {
        doReturn( getRace( ) ).when( racesRepo ).save( any( ) );
        doReturn( Optional.of( getRunner1( ) ) ).when( runnersRepo ).findById( RUNNER_1ID );
        doReturn( Optional.of( getRunner2( ) ) ).when( runnersRepo ).findById( RUNNER_2ID );
        Assertions.assertThat( raceService.addRace( "", getRaceRequest( ) ) ).isEqualTo( getRace( ) );
    }

    @Test
    public void finishRace( ) {
        doReturn( Optional.of( getRace( ) ) ).when( racesRepo ).findById( any( ) );
        doReturn( getRaceFinished( ) ).when( racesRepo ).save( any( ) );
        doReturn( getBetsList( ) ).when( betsRepo ).findALLByRace( getRace( ) );
        Assertions.assertThat( raceService.finishRace( "", getRaceFinishRequest( ) ) ).isEqualTo( getRaceFinished( ) );
    }

    @Test
    public void adminResponse( ) {
        doReturn( getRaces( ) ).when( racesRepo ).findAllByFinished( false );
        Assertions.assertThat( raceService.getAdminResponse( "" ).getRaces( ) ).usingFieldByFieldElementComparator( ).hasSameElementsAs( adminResp( ).getRaces( ) );
    }

    @Test
    public void userResponse( ) {
          doReturn( Optional.of( getUser( ) ) ).when( userRepo ).findByUsername( any( ) );
          doReturn( getUserUserDto( ) ).when( userService ).getUserDto( any( ) );
        doReturn( getRaces( )  ).when( racesRepo ).findAllByFinished( false );
        doReturn( getBetsList( ) ).when( betsRepo ).findALLByUser( any( ));
        doReturn( getRaces( ) ).when( racesRepo ).findAllByFinished( false );
        Assertions.assertThat( raceService.getUserResponse( "" ).getFinishedRaces( ).size() ).isEqualTo(1);
        Assertions.assertThat( raceService.getUserResponse( "" ).getAvailableRaces( ) ).isEmpty();
        Assertions.assertThat( raceService.getUserResponse( "" ).getBettedRaces( ).size() ).isEqualTo(1);
    }

    private UserDTO getUserUserDto( ) {
        UserDTO dto = new UserDTO( );
        dto.setRole( "user" );
        dto.setUsername( NAME );
        return dto;
    }

    private List<Race> getRaces( ) {
        List<Race> races = new ArrayList<>( );
        races.add( getRace( ) );
        return races;
    }

    private Bet getBet( ) {
        Bet bet = new Bet( );
        bet.setRace( getRace( ) );
        bet.setUser( getUser( ) );
        bet.setBet( 100D );
        bet.setWin( -100D );
        bet.setRunner_id( getRunner1( ) );
        bet.setId( BETID );
        return bet;
    }

    private RacesAdminResponse adminResp( ) {
        RacesAdminResponse resp = new RacesAdminResponse( );
        List<Race> races = new ArrayList<>( );
        races.add( getRace( ) );
        resp.setRaces( races );
        return resp;
    }

    private RacesUserResponse userResp( ) {
        RacesUserResponse resp = new RacesUserResponse( );
        List<Race> races = new ArrayList<>( );
        races.add( getRace( ) );
        resp.setAvailableRaces( races );
        return resp;
    }

    private Runner getRunner1( ) {
        Runner runner = new Runner( );
        runner.setId( RUNNER_1ID );
        runner.setRunner_name( NAME );
        return runner;
    }

    private Runner getRunner2( ) {
        Runner runner = new Runner( );
        runner.setId( RUNNER_2ID );
        runner.setRunner_name( NAME );
        return runner;
    }


    private Race getRace( ) {
        Race race = new Race( );
        race.setId( RACE_ID );
        race.setName( RACE1 );
        race.setRunner_1( getRunner1( ) );
        race.setRunner_2( getRunner2( ) );
        race.setFinished( false );
        race.setCoef1( COEF_1 );
        race.setCoef2( COEF_2 );
        return race;
    }

    private Race getRace2( ) {
        Race race = new Race( );
        race.setId( RACE_ID );
        race.setName( RACE1 );
        race.setRunner_1( getRunner1( ) );
        race.setRunner_2( getRunner2( ) );
        race.setFinished( true );
        race.setCoef1( COEF_1 );
        race.setCoef2( COEF_2 );
        return race;
    }

    private Race getRaceFinished( ) {
        Race race = new Race( );
        race.setId( RACE_ID );
        race.setName( RACE1 );
        race.setRunner_1( getRunner1( ) );
        race.setRunner_2( getRunner2( ) );
        race.setFinished( true );
        race.setCoef1( COEF_1 );
        race.setCoef2( COEF_2 );
        return race;
    }

    private RaceRequest getRaceRequest( ) {
        RaceRequest race = new RaceRequest( );
        race.setName( RACE1 );
        race.setRunner_id1( RUNNER_1ID );
        race.setRunner_id2( RUNNER_2ID );
        race.setCoef1( COEF_1 );
        race.setCoef2( COEF_2 );
        return race;
    }

    private RaceFinishRequest getRaceFinishRequest( ) {
        RaceFinishRequest race = new RaceFinishRequest( );
        race.setRace_id( RACE_ID );
        race.setWinner_coef( 2D );
        race.setWinner_id( RUNNER_1ID );
        return race;
    }

    private User getUser( ) {
        User user = new User( );
        user.setUsername( NAME );
        user.setId( USERID );
        return user;
    }

    private List<Bet> getBetsList( ) {
        Bet bet = new Bet( );
        bet.setRace( getRace( ) );
        bet.setUser( getUser( ) );
        bet.setBet( 100D );
        bet.setWin( -100D );
        bet.setRunner_id( getRunner1( ) );
        bet.setId( BETID );
        Bet bet2 = new Bet( );
        bet2.setRace( getRace2( ) );
        bet2.setUser( getUser( ) );
        bet2.setBet( 100D );
        bet2.setWin( -100D );
        bet2.setRunner_id( getRunner1( ) );
        bet2.setId( 2L );
        return Arrays.asList( bet,bet2 );
    }
}
