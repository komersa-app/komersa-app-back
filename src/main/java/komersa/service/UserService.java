package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.User;
import komersa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        log.info("User create: {}", user);

        return userRepository.save(user);
    }

    public User getById(long id) {
        log.info("User get by id: {}", id);
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id: " + id + " does not exist"));
    }

    public Page<User> getAll(Pageable pageable) {
        log.info("User get all: {}", pageable);
        return userRepository.findAll(pageable);
    }

    public User updateById(long id, User user) {
        getById(id);
        user.setUsrId(id);

        log.info("User update by id: {}", user);
        return userRepository.save(user);
    }

    public Boolean deleteById(long id) {
        log.info("User delete by id: {}", id);
        userRepository.deleteById(id);
        return true;
    }
}
