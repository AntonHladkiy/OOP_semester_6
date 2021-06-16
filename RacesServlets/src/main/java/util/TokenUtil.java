package util;


import entity.User;
import repo.UserRepo;
import service.AuthorizationService;

import java.util.Optional;

public final class TokenUtil {

  private static UserRepo userRepo = UserRepo.INSTANCE;
  public static User getUserByToken(String token) {
    Integer userId = AuthorizationService.getUserId(token);
    Optional<User> optionalUser = userRepo.findById(userId);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else throw new RuntimeException("e");
  }

  private TokenUtil() {}
}
