package mappers;

import entity.Runner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunnerMapper {
    public static final RunnerMapper INSTANCE = new RunnerMapper();
    private RunnerMapper() {}
    public Runner resultSetToEntity(ResultSet resultSet) throws SQLException {
        Runner runner = new Runner();
        runner.setId(resultSet.getInt("id"));
        runner.setName(resultSet.getString("runner_name"));
        return runner;
    }
}
