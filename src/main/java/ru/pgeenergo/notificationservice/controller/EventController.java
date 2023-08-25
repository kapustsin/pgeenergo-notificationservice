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
import ru.pgeenergo.notificationservice.domain.Event;
import ru.pgeenergo.notificationservice.service.EventService;

@RestController()
@RequestMapping("/api")
public class EventController {
    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/event")
    public ResponseEntity<String> create(@RequestBody Event event) {
        return new ResponseEntity<>("Event created successfully. Id = " + service.create(event), HttpStatus.CREATED);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> get(@PathVariable long id) {
        return ResponseEntity.of(service.get(id));
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody Event event) {
        if (service.exists(id)) {
            return new ResponseEntity<>("Event " + service.update(event) + " updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        if (service.exists(id)) {
            service.delete(id);
            return new ResponseEntity<>("Event " + id + " deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}