package bets.controllers;

import bets.dto.request.RaceFinishRequest;
import bets.dto.request.RaceRequest;
import bets.dto.response.RacesAdminResponse;
import bets.dto.response.RacesUserResponse;
import bets.entity.Race;
import bets.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/races")
public class RaceController {
    private final RaceService raceService;

    @Autowired
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @RolesAllowed({"admin"})
    @GetMapping("/admin")
    public ResponseEntity<RacesAdminResponse> getRacesAdmin(@RequestHeader String Authorization) {
        return ResponseEntity.ok( raceService.getAdminResponse( Authorization ) );
    }

    @RolesAllowed({"user"})
    @GetMapping("/user")
    public ResponseEntity<RacesUserResponse> getRacesUser(@RequestHeader String Authorization) {
        return ResponseEntity.ok( raceService.getUserResponse( Authorization ) );
    }

    @RolesAllowed("bookmaker")
    @PostMapping
    public ResponseEntity<Race> addRace(@RequestHeader String Authorization, @RequestBody RaceRequest request) {
        return ResponseEntity.ok( raceService.addRace( Authorization, request ) );
    }

    @CrossOrigin
    @RolesAllowed("admin")
    @PutMapping
    public ResponseEntity<Race> finishRace(@RequestHeader String Authorization, @RequestBody RaceFinishRequest request) {
        return ResponseEntity.ok( raceService.finishRace( Authorization, request ) );
    }
}
