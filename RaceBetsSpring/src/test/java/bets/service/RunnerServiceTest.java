package bets.service;

import bets.entity.Runner;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RunnerServiceTest {
    private static final Long RUNNER_1ID = 1L;
    private static final Long RUNNER_2ID = 2L;
    private static final String NAME = "name";

    @InjectMocks
    private RunnerService runnerService;

    @Mock
    private RunnersRepo runnersRepo;
    @Test
    public void checkRunners() {
        doReturn( getRunners()).when(runnersRepo).findAll();
        Assertions.assertThat( runnerService.getRunners( "" ).getRunners() ).usingFieldByFieldElementComparator( ).hasSameElementsAs( getRunners() );
    }

    private List<Runner> getRunners( ) {
        List<Runner> runners=new ArrayList<>(  );
        runners.add( getRunner1() );
        runners.add( getRunner2() );
        return runners;
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
}
