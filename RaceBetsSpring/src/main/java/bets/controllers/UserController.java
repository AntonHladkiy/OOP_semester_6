package bets.controllers;
import bets.dto.UserDTO;
import bets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed({"admin", "user", "bookmaker"})
    @GetMapping
    public ResponseEntity<UserDTO> getUserInfo(@RequestHeader String Authorization) {
        UserDTO userDTO = userService.afterLogIn(Authorization);
        return ResponseEntity.ok(userDTO);
    }
}