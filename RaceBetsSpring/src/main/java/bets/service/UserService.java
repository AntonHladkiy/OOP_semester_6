package bets.service;

import bets.dto.UserDTO;
import bets.entity.User;
import bets.repo.UserRepo;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO afterLogIn(String Authorization) {
        UserDTO userDTO = getUserDto(Authorization);
        checkIfUserIsSavedToDataBase(userDTO);
        return userDTO;
    }

    public UserDTO getUserDto(String Authorization) {
        UserDTO userDTO = new UserDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
                KeycloakPrincipal<KeycloakSecurityContext> kp =
                        (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();

                AccessToken accessToken = kp.getKeycloakSecurityContext().getToken();
                Set<String> roles = accessToken.getResourceAccess().get("bets-app").getRoles();
                Optional<String> optionalRole = roles.stream().findFirst();
                optionalRole.ifPresent(userDTO::setRole);
                userDTO.setUsername(accessToken.getPreferredUsername());
            }
        }
        return userDTO;
    }

    public void checkIfUserIsSavedToDataBase(UserDTO userDTO) {
        Optional<User> optionalUser = userRepo.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent()) {
            return;
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        userRepo.save(user);
    }
}