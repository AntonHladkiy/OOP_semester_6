package bets.controllers;

import bets.dto.UserDTO;
import bets.dto.response.BookmakerRunnersResponse;
import bets.service.RunnerService;
import bets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/runners")
public class RunnerController {
    private final RunnerService runnerService;

    @Autowired
    public RunnerController(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @RolesAllowed({"bookmaker"})
    @GetMapping
    public ResponseEntity<BookmakerRunnersResponse> getAllRunners(@RequestHeader String Authorization) {
        return  ResponseEntity.ok(runnerService.getRunners(Authorization));
    }
}
