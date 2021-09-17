package ir.bigZ.springboot.flywayapp.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDataAccessService implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> selectUsers() {
        var sql = " SELECT user_id, username \n" +
                "FROM USERS \n" +
                "LIMIT 100; ";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public int insertUser(User user) {
        var sql = " INSERT INTO USERS (username)\n" +
                " VALUES (?);";
        return jdbcTemplate.update(sql, user.getUsername());
    }

    @Override
    public int deleteUser(int id) {
        var sql = " DELETE FROM USERS \n" +
                " WHERE user_id = ?;";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> selectUserById(int id) {
        var sql = " SELECT user_id, username \n" +
                "FROM USERS \n" +
                "WHERE user_id = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), id)
                .stream()
                .findFirst();
    }
}
