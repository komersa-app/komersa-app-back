package komersa.service;

import komersa.domain.LoginAttempt;
import komersa.repository.LoginAttemptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LoginService {

    private final LoginAttemptRepository repository;

    public LoginService(LoginAttemptRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void addLoginAttempt(String name, boolean success) {
        LoginAttempt loginAttempt = new LoginAttempt(name, success, LocalDateTime.now());
        repository.save(loginAttempt);
    }

    public List<LoginAttempt> findRecentLoginAttempts(String name) {
        return repository.findByName(name).stream().limit(10L).toList();
    }
}
