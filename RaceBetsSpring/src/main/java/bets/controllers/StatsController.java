package bets.controllers;

import bets.dto.request.AddBetRequest;
import bets.dto.request.BetRequest;
import bets.dto.response.BookmakerStatsResponse;
import bets.dto.response.UserStatsResponse;
import bets.entity.Bet;
import bets.service.BetService;
import bets.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }


    @RolesAllowed({"user"})
    @GetMapping("/user")
    public ResponseEntity<UserStatsResponse> getUserStats(@RequestHeader String Authorization) {
        return  ResponseEntity.ok(statsService.getStatsForUser(Authorization));
    }

    @RolesAllowed({"bookmaker"})
    @GetMapping("/bookmaker")
    public ResponseEntity<BookmakerStatsResponse> getBookmakerStats(@RequestHeader String Authorization) {
        return  ResponseEntity.ok(statsService.getStatsForBookmaker(Authorization));
    }
}
