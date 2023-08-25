package ru.pgeenergo.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgeenergo.notificationservice.domain.User;
import ru.pgeenergo.notificationservice.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public ResponseEntity<String> create(@RequestBody User user) {
        return new ResponseEntity<>("User created successfully. Id = " + service.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable long id) {
        return ResponseEntity.of(service.get(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody User user) {
        if (service.exists(id)) {
            return new ResponseEntity<>("User " + service.update(user) + " updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        if (service.exists(id)) {
            service.delete(id);
            return new ResponseEntity<>("User " + id + " deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}