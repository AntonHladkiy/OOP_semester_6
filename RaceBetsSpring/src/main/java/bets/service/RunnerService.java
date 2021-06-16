package bets.service;

import bets.dto.response.BookmakerRunnersResponse;
import bets.entity.Runner;
import bets.repo.RunnersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunnerService {
    private final RunnersRepo runnersRepo;

    public RunnerService(RunnersRepo runnersRepo) {
        this.runnersRepo = runnersRepo;
    }

    public BookmakerRunnersResponse getRunners(String Authorization) {
        BookmakerRunnersResponse response = new BookmakerRunnersResponse();
        List<Runner> runners=runnersRepo.findAll();
        response.setRunners( runners );
        return response;
    }


}
