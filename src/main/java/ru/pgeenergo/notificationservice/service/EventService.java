package ru.pgeenergo.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pgeenergo.notificationservice.domain.Event;
import ru.pgeenergo.notificationservice.repository.EventRepository;

import java.util.Optional;

@Service
public class EventService {
    private final EventRepository repository;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public long create(Event event) {
        return repository.save(event).getId();
    }

    public long update(Event event) {
        return repository.save(event).getId();
    }

    public Optional<Event> get(long eventId) {
        return repository.findById(eventId);
    }

    public void delete(long eventId) {
        repository.deleteById(eventId);
    }

    public boolean exists(long eventId) {
        return repository.existsById(eventId);
    }
}