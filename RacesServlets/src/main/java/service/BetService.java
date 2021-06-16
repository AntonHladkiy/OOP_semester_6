package service;

import entity.Bet;
import entity.Race;
import entity.User;
import entity.request.AddBetRequest;
import entity.request.BetRequest;
import entity.request.RaceRequest;
import repo.BetRepo;
import repo.RaceRepo;
import util.TokenUtil;

public class BetService {
    public static BetService INSTANCE = new BetService( );

    private final BetRepo betRepo = BetRepo.INSTANCE;

    public Bet save(BetRequest request) {
        User user = TokenUtil.getUserByToken( request.getToken( ) );
        Bet bet = getBetFromRequest( request, user );
        return betRepo.save( bet );
    }

    private Bet getBetFromRequest(BetRequest request, User user) {
        Bet bet = new Bet( );
        bet.setWin( -request.getBet( ) );
        bet.setUser_id( user.getId( ) );
        bet.setRunner_id( request.getRunner_id( ) );
        bet.setRace_id( request.getRace_id( ) );
        bet.setBet( request.getBet( ) );
        return bet;

    }

    public void addBet(AddBetRequest request) {
        User user = TokenUtil.getUserByToken( request.getToken( ) );
        betRepo.addBet( user.getId(),request );
    }
}
