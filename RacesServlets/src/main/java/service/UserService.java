package service;

import db.ConnectionFactory;
import entity.User;
import entity.request.UserCreateRequest;
import repo.UserRepo;
import util.Encryptor;

import java.sql.Connection;
import java.util.Optional;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepo userRepo = UserRepo.INSTANCE;

    private UserService() {}

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getByUsername(String username) {
        User user =
                findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                String.format("Can't find user by username=%s", username)));
        return user;
    }

    public User create(UserCreateRequest request) {

        if (findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User with username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(Encryptor.encode(request.getPassword()));
        user.setRole(request.getRole());

        Connection connection = ConnectionFactory.getConnection();

        try {
            ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
            user = userRepo.save(user);
            ConnectionFactory.commitTransaction(connection);
            return user;
        } catch (Exception e) {
            ConnectionFactory.rollbackTransaction(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(connection);
        }
    }
}
