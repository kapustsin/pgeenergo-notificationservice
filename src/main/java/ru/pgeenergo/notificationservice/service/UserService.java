package ru.pgeenergo.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pgeenergo.notificationservice.domain.User;
import ru.pgeenergo.notificationservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public long create(User user) {
        return repository.save(user).getId();
    }

    public long update(User user) {
        return repository.save(user).getId();
    }

    public Optional<User> get(long userId) {
        return repository.findById(userId);
    }

    public void delete(long userId) {
        repository.deleteById(userId);
    }

    public boolean exists(long userId) {
        return repository.existsById(userId);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}