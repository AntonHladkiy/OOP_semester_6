package service;

import entity.Runner;
import entity.User;
import entity.response.RunnerBookMakerResponse;
import repo.RunnerRepo;
import util.TokenUtil;

import java.util.List;

public class RunnerService {
    public static RunnerService INSTANCE = new RunnerService();
    private RunnerRepo runnerRepo=RunnerRepo.INSTANCE;

    public RunnerBookMakerResponse getBookMakerResponse(String token) {
        RunnerBookMakerResponse response = new RunnerBookMakerResponse();
        User user=TokenUtil.getUserByToken( token );
        if(!user.getRole( ).equals( "Bookmaker" )){
            throw new RuntimeException("not bookmaker");
        }
        List<Runner> runnerResponses=runnerRepo.findAllRunners();
        response.setRunners( runnerResponses );
        return response;
    }
}
