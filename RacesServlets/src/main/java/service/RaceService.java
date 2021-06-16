package service;

import entity.Bet;
import entity.Race;
import entity.User;
import entity.request.RaceFinishRequest;
import entity.request.RaceRequest;
import entity.response.*;
import repo.BetRepo;
import repo.RaceRepo;
import util.TokenUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RaceService {
    public static RaceService INSTANCE = new RaceService();

    private final RaceRepo raceRepo = RaceRepo.INSTANCE;

    public Race save(RaceRequest request) {
        User user= TokenUtil.getUserByToken( request.getToken());
        if(!user.getRole( ).equals( "Bookmaker" )){
            throw new RuntimeException("not bookmaker");
        }
        Race race = getRaceFromRequest(request);
        return raceRepo.save(race);
    }

    private Race getRaceFromRequest(RaceRequest request) {
        Race race = new Race();
        race.setCoef1(request.getCoef1());
        race.setCoef2(request.getCoef2());
        race.setName(request.getName());
        race.setRunner_id1(request.getRunner_id1());
        race.setRunner_id2(request.getRunner_id2());
        return race;
    }

    public RacesAdminResponse getAdminResponse() {
        RacesAdminResponse response=new RacesAdminResponse();
        response.setRaces( RaceRepo.INSTANCE.findAllNotFinishedRaces());
        return response;
    }

    public void finishRace(RaceFinishRequest request) {
        RaceRepo.INSTANCE.finishRaces(request.getRace_id());
        List<Bet> bets=BetRepo.INSTANCE.getAllBetsForRace(request.getRace_id());
        for (Bet bet : bets) {
            BetRepo.INSTANCE.calculateBet( bet,request );
        }
    }

    public RacesUserResponse getUserResponse( Integer user_id) {
        RacesUserResponse response=new RacesUserResponse();
        List<RaceResponseBetted> betRaces=RaceRepo.INSTANCE.findAllNotFinishedRacesWithUserBets(user_id);
        response.setBettedRaces( betRaces );
        List<RaceResponseFinished> finishedRaces=RaceRepo.INSTANCE.findFinishedRacesForUser(user_id);
        response.setFinishedRaces( finishedRaces );
        List<RaceResponseAvailable> allRacesNotFinished=RaceRepo.INSTANCE.findAllNotFinishedRaces();
        Set<Integer> allNotFinished = new HashSet<>();
        for (RaceResponseAvailable race : allRacesNotFinished) {
            allNotFinished.add(race.getRace_id());
        }
        Set<Integer> notFinishedWithBets = new HashSet<>();
        for (RaceResponseBetted race : betRaces) {
            notFinishedWithBets.add(race.getRace_id());
        }
        allNotFinished.removeAll(notFinishedWithBets);
        List<RaceResponseAvailable> racesAvailable=new ArrayList<>();
        for (RaceResponseAvailable race : allRacesNotFinished) {
            if (allNotFinished.contains(race.getRace_id())) {
                racesAvailable.add(race);
            }
        }
        response.setAvailableRaces( racesAvailable );
        return response;
    }
}
