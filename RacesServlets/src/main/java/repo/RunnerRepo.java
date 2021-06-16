package repo;

import db.ConnectionFactory;
import entity.Runner;
import entity.User;
import mappers.RunnerMapper;
import mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunnerRepo {
    public static final RunnerRepo INSTANCE = new RunnerRepo();

    public List<Runner> findAllRunners( ) {
        List<Runner> allRunners=new ArrayList<>(  );
        String command = "SELECT * FROM runners";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allRunners.add( RunnerMapper.INSTANCE.resultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allRunners;
    }
    public Optional<Runner> findById(Integer id) {
        String command = "SELECT * FROM runners WHERE id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of( RunnerMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
