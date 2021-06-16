package bets.controllers;

import bets.dto.request.RaceRequest;
import bets.dto.response.AdminStatsResponse;
import bets.entity.Race;
import bets.service.BookmakerDataService;
import bets.service.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/bookmaker/stats")
public class BookmakerStatsController {
    private final BookmakerDataService bookmakerDataService;

    public BookmakerStatsController(BookmakerDataService bookmakerDataService) {
        this.bookmakerDataService = bookmakerDataService;
    }

    @RolesAllowed("admin")
    @GetMapping
    public ResponseEntity<AdminStatsResponse> getStats(@RequestHeader String Authorization) {
        return ResponseEntity.ok( bookmakerDataService.getStats( Authorization) );
    }
}
