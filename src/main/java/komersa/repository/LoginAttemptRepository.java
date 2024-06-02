package komersa.repository;

import komersa.domain.LoginAttempt;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class LoginAttemptRepository {

  private static final int RECENT_COUNT = 10; // can be in the config
  private static final String INSERT = "INSERT INTO authentication.login_attempt (email, success, created_at) VALUES(:name, :success, :createdAt)";
  private static final String FIND_RECENT = "SELECT * FROM authentication.login_attempt WHERE name = :name ORDER BY created_at DESC LIMIT :recentCount";

  private final JdbcClient jdbcClient;

  public LoginAttemptRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public void add(LoginAttempt loginAttempt) {
    long affected = jdbcClient.sql(INSERT)
        .param("name", loginAttempt.name())
        .param("success", loginAttempt.success())
        .param("createdAt", loginAttempt.createdAt())
        .update();

    Assert.isTrue(affected == 1, "Could not add login attempt.");
  }

  public List<LoginAttempt> findRecent(String email) {
    return jdbcClient.sql(FIND_RECENT)
        .param("email", email)
        .param("recentCount", RECENT_COUNT)
        .query(LoginAttempt.class)
        .list();
  }
}
