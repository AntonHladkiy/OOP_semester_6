package bets.service;

import bets.dto.UserDTO;
import bets.entity.User;
import bets.repo.UserRepo;
import bets.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {


    @InjectMocks
    UserService userService;

    @Mock private UserRepo userRepo;

    @Test
    public void checkIfUserIsSavedToDataBase_User() {
        Assertions.assertThatCode(() -> userService.checkIfUserIsSavedToDataBase(getUserUserDto()))
                .doesNotThrowAnyException();
    }

    @Test
    public void checkIfUserIsSavedToDataBase_Admin() {
        Assertions.assertThatCode(() -> userService.checkIfUserIsSavedToDataBase(getUserAdminDto()))
                .doesNotThrowAnyException();
    }
    @Test
    public void checkIfUserIsSavedToDataBase_Bookmaker() {
        Assertions.assertThatCode(() -> userService.checkIfUserIsSavedToDataBase(getUserBookmakerDto()))
                .doesNotThrowAnyException();
    }


    private UserDTO getUserUserDto() {
        UserDTO dto = new UserDTO();
        dto.setRole("user");

        return dto;
    }

    private UserDTO getUserAdminDto() {
        UserDTO dto = new UserDTO();
        dto.setRole("admin");

        return dto;
    }

    private UserDTO getUserBookmakerDto() {
        UserDTO dto = new UserDTO();
        dto.setRole("bookmaker");

        return dto;
    }


    private User getUser() {
        User user = new User();
        return user;
    }
}