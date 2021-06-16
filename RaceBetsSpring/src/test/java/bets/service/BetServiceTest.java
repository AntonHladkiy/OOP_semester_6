package bets.service;

import bets.dto.UserDTO;
import bets.dto.request.AddBetRequest;
import bets.dto.request.BetRequest;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BetServiceTest {
    private static final Long BETID = 1L;
    private static final Long USERID = 1L;
    private static final Long RUNNER_ID = 1L;
    private static final Long RUNNER_1ID = 1L;
    private static final Long RUNNER_2ID = 2L;
    private static final Long RACE_ID = 1L;
    private static final Double COEF_1 = 1D;
    private static final Double COEF_2 = 1D;
    private static final String NAME = "name";
    private static final String RACE1 = "race1";


    @InjectMocks
    private BetService betService;

    @Mock
    private RacesRepo racesRepo;

    @Mock
    private BetsRepo betsRepo;

    @Mock
    private RunnersRepo runnersRepo;
    @Mock
    private UserService userService;

    @Mock private UserRepo userRepo;
    @Test
    public void addBet( ) {
        doReturn( Optional.of(getUser()) ).when( userRepo ).findByUsername( any( ) );
        doReturn( getUserUserDto( ) ).when( userService ).getUserDto( any() );
        doReturn( Optional.of(getRace()) ).when( racesRepo ).findById( RACE_ID );
        doReturn( Optional.of(getRunner1( )) ).when( runnersRepo ).findById( RUNNER_ID );
        doReturn( getBet( ) ).when( betsRepo ).save( any() );
        Assertions.assertThat( betService.addBet( "", getBetRequest( ) ) ).isEqualTo( getBet( ) );
    }
    @Test
    public void changeBet( ) {
        doReturn( Optional.of(getUser()) ).when( userRepo ).findByUsername( any( ) );
        doReturn( getUserUserDto( ) ).when( userService ).getUserDto( any() );
        doReturn( Optional.of(getRace()) ).when( racesRepo ).findById( RACE_ID );
        doReturn( Optional.of(getBet()) ).when( betsRepo ).findByRaceAndUser( any(),any() );
        doReturn( getBet( ) ).when( betsRepo ).save( any() );
        Assertions.assertThat( betService.changeBet( "", getChangeBetRequest( ) ) ).isEqualTo( getBet( ) );
    }

    private AddBetRequest getChangeBetRequest( ) {
        AddBetRequest req=new AddBetRequest();
        req.setAdditionalBet( 1D );
        req.setCurrentBet( 1D );
        req.setRace_id( RACE_ID );
        return req;
    }

    private UserDTO getUserUserDto() {
        UserDTO dto = new UserDTO();
        dto.setRole("user");
        dto.setUsername( NAME );
        return dto;
    }
    private User getUser() {
        User user = new User();
        user.setId( USERID );
        user.setUsername( NAME );
        return user;
    }
    private BetRequest getBetRequest( ) {
        BetRequest bet = new BetRequest( );
        bet.setBet( 100D );
        bet.setRace_id( RACE_ID );
        bet.setRunner_id( RUNNER_ID );
        return bet;
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
    private Bet getBet( ) {
        Bet bet = new Bet( );
        bet.setRace(getRace() );
        bet.setUser(getUser() );
        bet.setBet( 100D );
        bet.setWin( -100D );
        bet.setRunner_id( getRunner1() );
        bet.setId( BETID );
        return bet ;
    }
}
