package repo;

import db.ConnectionFactory;
import entity.Race;
import entity.response.RaceResponseAvailable;
import entity.response.RaceResponseBetted;
import entity.response.RaceResponseFinished;
import mappers.RaceMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaceRepo {
    public static final RaceRepo INSTANCE = new RaceRepo();

    public Race save(Race race) {
        String command = "INSERT INTO races (name, runner_id1,runner_id2,coef1,coef2,finished) VALUES (?, ?,?,?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, race.getName());
            preparedStatement.setInt(2, race.getRunner_id1());
            preparedStatement.setInt(3, race.getRunner_id2());
            preparedStatement.setDouble(4, race.getCoef1());
            preparedStatement.setDouble(5, race.getCoef2());
            preparedStatement.setBoolean(6,false);
            
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                race.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return race;
    }

    public List<RaceResponseAvailable> findAllNotFinishedRaces() {
        List<RaceResponseAvailable> races=new ArrayList<>( );
        String command = "SELECT * FROM races WHERE finished=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                races.add( RaceMapper.INSTANCE.resultSetToResponse(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return races;
    }
    public List<RaceResponseBetted> findAllNotFinishedRacesWithUserBets(Integer user_id) {
        List<RaceResponseBetted> races=new ArrayList<>( );
        String command = "SELECT * FROM races INNER JOIN bets b on races.id = b.race_id WHERE finished=? AND user_id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                races.add( RaceMapper.INSTANCE.resultSetToResponseWithBet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return races;
    }
    public void finishRaces(Integer race_id) {
        String command = "UPDATE races SET finished=?WHERE id=? ";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, race_id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<RaceResponseFinished> findFinishedRacesForUser(Integer user_id) {
        List<RaceResponseFinished> races=new ArrayList<>( );
        String command = "SELECT * FROM races INNER JOIN bets b on races.id = b.race_id WHERE user_id=? AND finished=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setBoolean(2, true);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                races.add( RaceMapper.INSTANCE.resultSetToResponseFinished(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return races;
    }
}
