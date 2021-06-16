package bets.service;

import bets.dto.UserDTO;
import bets.dto.response.BookmakerStatsResponse;
import bets.dto.response.UserStatsResponse;
import bets.entity.Bet;
import bets.entity.Runner;
import bets.entity.User;
import bets.repo.BetsRepo;
import bets.repo.RunnersRepo;
import bets.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatsService {
    private final BetsRepo betsRepo;
    private final RunnersRepo runnersRepo;
    private final UserRepo userRepo;
    private final UserService userService;

    public StatsService(BetsRepo betsRepo, RunnersRepo runnersRepo, UserRepo userRepo, UserService userService) {
        this.betsRepo = betsRepo;
        this.runnersRepo = runnersRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    public UserStatsResponse getStatsForUser(String authorization) {
        UserStatsResponse resp = new UserStatsResponse( );
        UserDTO userDTO = userService.getUserDto( authorization );
        Optional<User> optionalUser = userRepo.findByUsername( userDTO.getUsername( ) );
        if (optionalUser.isEmpty( )) {
            throw new RuntimeException( "no such user" );
        }
        User user = optionalUser.get( );
        List<Bet> betsUsers = betsRepo.findALLByUser( user );
        Double overall = 0.0;
        Integer count = betsUsers.size( );
        for ( Bet bet : betsUsers ) {
            overall += bet.getWin( );
        }
        resp.setOverall( overall );
        resp.setAverage( overall / count );
        return resp;
    }

    public BookmakerStatsResponse getStatsForBookmaker(String authorization) {
        BookmakerStatsResponse resp = new BookmakerStatsResponse( );
        UserDTO userDTO = userService.getUserDto( authorization );
        Optional<User> optionalUser = userRepo.findByUsername( userDTO.getUsername( ) );
        if (optionalUser.isEmpty( )) {
            throw new RuntimeException( "no such user" );
        }
        User user = optionalUser.get( );

        List<User> users = userRepo.findAll( );
        User bestBetter = null;
        double maxOverall = -Double.MAX_VALUE;
        for ( User value : users ) {
            if(value.getUsername( ).equals( "bookmaker" ) ||value.getUsername( ).equals( "admin" ) ){
                continue;
            }
            Double overall = 0.0;
            List<Bet> betsUsers = betsRepo.findALLByUser( value );
            for ( Bet bet : betsUsers ) {
                overall += bet.getWin( );
            }
            if (overall >= maxOverall) {
                bestBetter = value;
                maxOverall=overall;
            }
        }
        resp.setBetter( bestBetter );
        resp.setBetterWins( maxOverall );
        List<Bet> allBets = betsRepo.findAll( );
        List<Runner> allRunners = runnersRepo.findAll( );

        List<Integer> winCount = new ArrayList<>( );
        for ( int i = 0; i < allRunners.size( ); i++ ) {
            winCount.add( 0 );
        }
        double maxCount = 0.0;
        Runner bestRunner = null;
        for ( Bet bet : allBets ) {
            Long id = bet.getRunner_id( ).getId( );
            winCount.set( id.intValue( ), winCount.get( id.intValue( ) ) + 1 );
            if (maxCount < winCount.get( id.intValue( ) )) {
                maxCount = winCount.get( id.intValue( ) );
                bestRunner = bet.getRunner_id( );
            }
        }
        resp.setRunner( bestRunner );
        if (bestRunner != null) {
            resp.setRunnerWins( winCount.get( bestRunner.getId( ).intValue( ) ) );
        }
        return resp;
    }
}
