package bets.service;

import bets.dto.UserDTO;
import bets.dto.request.AddBetRequest;
import bets.dto.request.BetRequest;
import bets.entity.Bet;
import bets.entity.Race;
import bets.entity.User;
import bets.repo.BetsRepo;
import bets.repo.RacesRepo;
import bets.repo.RunnersRepo;
import bets.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BetService {
    private final BetsRepo betsRepo;
    private final UserRepo userRepo;
    private final RacesRepo racesRepo;
    private final RunnersRepo runnersRepo;
    private final UserService userService;

    public BetService(BetsRepo betsRepo, UserRepo userRepo, RacesRepo racesRepo, RunnersRepo runnersRepo, UserService userService) {
        this.betsRepo = betsRepo;
        this.userRepo = userRepo;
        this.racesRepo = racesRepo;
        this.runnersRepo = runnersRepo;
        this.userService = userService;
    }


    public Bet addBet(String authorization, BetRequest request) {
        UserDTO userDTO=userService.getUserDto( authorization);
        Optional<User> optionalUser = userRepo.findByUsername(userDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("no such user");
        }
        User user = optionalUser.get();
        Bet bet=new Bet();
        bet.setBet( request.getBet() );
        racesRepo.findById( request.getRace_id()).ifPresent( bet::setRace );
        bet.setUser( user );
        bet.setWin(-request.getBet()  );
        runnersRepo.findById( request.getRunner_id()).ifPresent( bet::setRunner_id );
        return betsRepo.save(bet);
    }

    public Bet changeBet(String authorization, AddBetRequest request) {
        UserDTO userDTO=userService.getUserDto( authorization);
        Optional<User> optionalUser = userRepo.findByUsername(userDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("no such user");
        }
        User user = optionalUser.get();
        Optional<Race> optionalRace = racesRepo.findById( request.getRace_id( ) );
        if (optionalRace.isEmpty()) {
            throw new RuntimeException("no such race");
        }
        Race race = optionalRace.get();
        Optional<Bet> optionalBet = betsRepo.findByRaceAndUser( race, user );
        if (optionalBet.isEmpty()) {
            throw new RuntimeException("no such bet");
        }
        Bet bet = optionalBet.get();
        bet.setBet( request.getAdditionalBet()+request.getCurrentBet() );
        racesRepo.findById( request.getRace_id()).ifPresent( bet::setRace );
        bet.setUser( user );
        bet.setWin(-(request.getAdditionalBet()+request.getCurrentBet())  );
        bet.setRunner_id( bet.getRunner_id() );
        return betsRepo.save(bet);
    }
}
