package bets.service;

import bets.dto.UserDTO;
import bets.dto.request.RaceFinishRequest;
import bets.dto.request.RaceRequest;
import bets.dto.response.BetRacesUserResponse;
import bets.dto.response.FinishedRacesUserResponse;
import bets.dto.response.RacesAdminResponse;
import bets.dto.response.RacesUserResponse;
import bets.entity.Bet;
import bets.entity.BookmakerData;
import bets.entity.Race;
import bets.entity.User;
import bets.repo.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RaceService {
    private final BetsRepo betsRepo;
    private final UserRepo userRepo;
    private final RacesRepo racesRepo;
    private final RunnersRepo runnersRepo;
    private final BookmakerDataRepo bookmakerDataRepo;
    private final UserService userService;

    public RaceService(BetsRepo betsRepo, UserRepo userRepo, RacesRepo racesRepo, RunnersRepo runnersRepo, BookmakerDataRepo bookmakerDataRepo, UserService userService) {
        this.betsRepo = betsRepo;
        this.userRepo = userRepo;
        this.racesRepo = racesRepo;
        this.runnersRepo = runnersRepo;
        this.bookmakerDataRepo = bookmakerDataRepo;
        this.userService = userService;
    }

    public Race addRace(String authorization, RaceRequest request) {
        UserDTO bookmaker =userService.getUserDto(authorization);
        Race race = new Race( );
        race.setCoef1( request.getCoef1( ) );
        race.setCoef2( request.getCoef2( ) );
        race.setFinished( false );
        race.setName( request.getName( ) );
        userRepo.findByUsername( bookmaker.getUsername() ).ifPresent( race::setBookmaker);
        runnersRepo.findById( request.getRunner_id1( ) ).ifPresent( race::setRunner_1 );
        runnersRepo.findById( request.getRunner_id2( ) ).ifPresent( race::setRunner_2 );
        return racesRepo.save( race );
    }

    public Race finishRace(String authorization, RaceFinishRequest request) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String current_date=(formatter.format(date));
        Optional<Race> optionalRace = racesRepo.findById( request.getRace_id( ) );
        if (optionalRace.isEmpty( )) {
            throw new RuntimeException( "no such race" );
        }
        Race race = optionalRace.get( );
        List<Bet> bets = betsRepo.findALLByRace(race);
        race.setCoef1( race.getCoef1( ) );
        race.setCoef2( race.getCoef2( ) );
        race.setFinished( true );
        race.setName( race.getName( ) );
        race.setRunner_1( race.getRunner_1( ) );
        race.setRunner_2( race.getRunner_2( ) );
        User bookmaker=race.getBookmaker();
        List<BookmakerData> bookmakerDatas=bookmakerDataRepo.findAllByDateAndBookmaker( java.sql.Date.valueOf(current_date),bookmaker );
        BookmakerData bookmakerData;
        if(bookmakerDatas.size()==0){
            bookmakerData = new BookmakerData();
            bookmakerData.setBookmaker( bookmaker );
            bookmakerData.setDate( java.sql.Date.valueOf(current_date ));
            List<BookmakerData> temp=bookmakerDataRepo.findAllByBookmaker( bookmaker );
            if(temp.size()==0){
                bookmakerData.setIncome( 0.0 );
            } else{
                temp.sort( new Comparator<BookmakerData>( ) {
                    @Override
                    public int compare(BookmakerData lhs, BookmakerData rhs) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return lhs.getId( ).compareTo( rhs.getId( ) );
                    }
                } );
                bookmakerData.setIncome( temp.get(0).getIncome() );
            }
        } else{
            bookmakerData=bookmakerDatas.get(0);
        }
        for ( Bet bet : bets ) {
            bet.setBet( bet.getBet( ) );
            bet.setRace( bet.getRace( ) );
            bet.setUser( bet.getUser( ) );
            if (bet.getRunner_id( ).getId( ).equals( request.getWinner_id( ) )) {
                bet.setWin( bet.getWin( ) * -1 * request.getWinner_coef( ) );
                bookmakerData.setIncome( bookmakerData.getIncome()+bet.getBet()-bet.getWin() );
            } else {
                bet.setWin( bet.getWin( ) );
                bookmakerData.setIncome( bookmakerData.getIncome()+bet.getBet());
            }
            bet.setRunner_id( bet.getRunner_id( ) );


            betsRepo.save( bet );
        }
        bookmakerDataRepo.save(bookmakerData);
        return racesRepo.save( race );
    }

    public RacesAdminResponse getAdminResponse(String authorization) {

        List<Race> races = racesRepo.findAllByFinished( false );
        RacesAdminResponse response = new RacesAdminResponse( );
        response.setRaces( races );
        return response;
    }

    public RacesUserResponse getUserResponse(String authorization) {
        UserDTO userDTO = userService.getUserDto( authorization );
        Optional<User> optionalUser = userRepo.findByUsername( userDTO.getUsername( ) );
        if (optionalUser.isEmpty( )) {
            throw new RuntimeException( "no such user" );
        }
        User user = optionalUser.get( );
        RacesUserResponse response = new RacesUserResponse( );
        List<Bet> bets = betsRepo.findALLByUser( user );
        List<BetRacesUserResponse> betRaces = new ArrayList<>( );
        List<FinishedRacesUserResponse> finishedRaces=new ArrayList<>(  );
        for ( Bet bet : bets ) {
            if (!bet.getRace( ).getFinished( )) {
                Double coef;
                if (bet.getRunner_id( ) == bet.getRace( ).getRunner_1( )) {
                    coef = bet.getRace( ).getCoef1( );
                } else {
                    coef = bet.getRace( ).getCoef2( );
                }
                betRaces.add( new BetRacesUserResponse(
                        bet.getRace( ).getId( ),
                        bet.getRace( ).getName( ),
                        coef,
                        bet.getRunner_id( ),
                        bet.getBet( )
                ) );
            } else {
                finishedRaces.add( new FinishedRacesUserResponse(
                        bet.getRace( ).getId( ),
                        bet.getRace( ).getName( ),
                        bet.getBet( ),
                        bet.getWin()
                ) );
            }
        }
        response.setBettedRaces( betRaces );
        response.setFinishedRaces( finishedRaces );
        List<Race> allRacesNotFinished = racesRepo.findAllByFinished(false);
        Set<Long> allNotFinished = new HashSet<>( );
        for ( Race race : allRacesNotFinished ) {
            allNotFinished.add( race.getId( ) );
        }
        Set<Long> notFinishedWithBets = new HashSet<>( );
        for ( BetRacesUserResponse race : betRaces ) {
            notFinishedWithBets.add( race.getRace_id( ) );
        }
        allNotFinished.removeAll( notFinishedWithBets );
        List<Race> racesAvailable = new ArrayList<>( );
        for ( Race race : allRacesNotFinished ) {
            if (allNotFinished.contains( race.getId( ) )) {
                racesAvailable.add( race );
            }
        }
        response.setAvailableRaces( racesAvailable );
        return response;
    }
}
