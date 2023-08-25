package ru.pgeenergo.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pgeenergo.notificationservice.domain.Event;
import ru.pgeenergo.notificationservice.domain.NotificationPeriod;
import ru.pgeenergo.notificationservice.domain.User;
import ru.pgeenergo.notificationservice.repository.EventRepository;
import ru.pgeenergo.notificationservice.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public long create(Event newEvent) {
        Event currentEvent = eventRepository.save(newEvent);
        sendNotifications(currentEvent, userRepository.findAll());
        return currentEvent.getId();
    }

    public long update(Event event) {
        return eventRepository.save(event).getId();
    }

    public Optional<Event> get(long eventId) {
        return eventRepository.findById(eventId);
    }

    public void delete(long eventId) {
        eventRepository.deleteById(eventId);
    }

    public boolean exists(long eventId) {
        return eventRepository.existsById(eventId);
    }

    private void sendNotifications(Event event, List<User> users) {
        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        for (User user : users) {
            List<NotificationPeriod> notificationPeriods = user.getNotificationTime();
            LocalTime now = LocalTime.now();
            for (NotificationPeriod period : notificationPeriods) {
                if (period.getDayOfWeek().getValue() == currentDayOfWeek && timeInRange(now, period)) {
                    String formattedDateTime = LocalDateTime.now().format(formatter);
                    logger.info("{} Пользователю {} отправлено оповещение с текстом: {}",
                            formattedDateTime, user.getName(), event.getMessage());
                    break;
                }
            }
        }
    }

    private boolean timeInRange(LocalTime now, NotificationPeriod period) {
        return now.isAfter(period.getBeginPeriod()) && now.isBefore(period.getEndPeriod());
    }
}