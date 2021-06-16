package repo;

import db.ConnectionFactory;
import entity.Bet;
import entity.Race;
import entity.request.AddBetRequest;
import entity.request.BetRequest;
import entity.request.RaceFinishRequest;
import mappers.BetMapper;
import mappers.RaceMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetRepo {
    public static final BetRepo INSTANCE = new BetRepo();
    public List<Bet> getAllBetsForRace(Integer race_id) {
        List<Bet> bets=new ArrayList<>( );
        String command = "SELECT * FROM bets WHERE race_id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDouble(1, race_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bets.add( BetMapper.INSTANCE.resultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bets;
    }
    public void calculateBet(Bet bet,RaceFinishRequest request) {
            String command = "UPDATE bets SET  win=? WHERE race_id=? AND runner_id=? AND user_id=?";
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setDouble(1,request.getWinner_coef()*bet.getBet() );
                preparedStatement.setDouble(2,bet.getRace_id() );
                preparedStatement.setDouble(3,request.getWinner_id() );
                preparedStatement.setDouble(4,bet.getUser_id() );
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }

    public Bet save(Bet bet) {
        String command = "INSERT INTO bets (race_id, user_id,bet,runner_id,win) VALUES (?, ?,?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, bet.getRace_id());
            preparedStatement.setInt(2, bet.getUser_id());
            preparedStatement.setDouble(3, bet.getBet());
            preparedStatement.setInt(4, bet.getRunner_id());
            preparedStatement.setDouble(5, bet.getWin());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bet;
    }

    public void addBet(Integer id, AddBetRequest request) {
        String command = "UPDATE bets SET  bet=?, win=? WHERE race_id=? AND user_id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDouble(1,request.getAdditionalBet()+request.getCurrentBet() );
            preparedStatement.setDouble(2,-(request.getAdditionalBet()+request.getCurrentBet() ));
            preparedStatement.setDouble(3,request.getRace_id() );
            preparedStatement.setDouble(4,id );
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
