package bets.controllers;

import bets.dto.request.AddBetRequest;
import bets.dto.request.BetRequest;
import bets.entity.Bet;
import bets.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/bets")
public class BetController {
    private final BetService betService;

    @Autowired
    public BetController(BetService betService) {
        this.betService = betService;
    }

    @CrossOrigin
    @RolesAllowed({"user"})
    @PutMapping
    public ResponseEntity<Bet> changeBet(@RequestHeader String Authorization, @RequestBody AddBetRequest request) {
        return  ResponseEntity.ok(betService.changeBet(Authorization, request));
    }

    @RolesAllowed({"user"})
    @PostMapping
    public ResponseEntity<Bet> addNewBet(@RequestHeader String Authorization, @RequestBody BetRequest request) {
        return  ResponseEntity.ok(betService.addBet(Authorization, request));
    }
}
