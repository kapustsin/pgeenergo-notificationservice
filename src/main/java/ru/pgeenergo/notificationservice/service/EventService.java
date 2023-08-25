package ru.pgeenergo.notificationservice.service;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.pgeenergo.notificationservice.domain.Event;
import ru.pgeenergo.notificationservice.domain.NotificationPeriod;
import ru.pgeenergo.notificationservice.domain.User;
import ru.pgeenergo.notificationservice.repository.EventRepository;
import ru.pgeenergo.notificationservice.repository.UserRepository;
import ru.pgeenergo.notificationservice.service.jobs.DelayedNotification;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.now;

@Service
public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final Scheduler scheduler;

    public EventService(EventRepository eventRepository, UserRepository userRepository, Scheduler scheduler) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.scheduler = scheduler;
    }

    public long create(Event newEvent) {
        Event currentEvent = eventRepository.save(newEvent);
        processNewEvent(currentEvent, userRepository.findAll());
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

    private void processNewEvent(Event event, List<User> users) {
        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        for (User user : users) {
            boolean notificationSent = false;
            List<NotificationPeriod> notificationPeriods = user.getNotificationTime();
            LocalTime now = now();
            for (NotificationPeriod period : notificationPeriods) {
                if (period.getDayOfWeek().getValue() == currentDayOfWeek && timeInRange(now, period)) {
                    sendNotification(user.getName(), event.getMessage(), formatter);
                    notificationSent = true;
                    break;
                }
            }
            if (!notificationSent) {
                createDelayedNotification(user, event.getMessage(), getClosestNotificationTime(notificationPeriods));
            }
        }
    }

    public void sendNotification(String userName, String eventMessage, DateTimeFormatter formatter) {
        String dateTime = LocalDateTime.now().format(formatter);
        logger.info("{} Пользователю {} отправлено оповещение с текстом: {}", dateTime, userName, eventMessage);
    }

    private void createDelayedNotification(User user, String eventMessage, long timestamp) {
        JobDetail jobDetail = JobBuilder.newJob(DelayedNotification.class)
                .withIdentity("delayedNotificationUserId" + user.getId())
                .storeDurably()
                .build();

        jobDetail.getJobDataMap().put("eventMessage", eventMessage);
        jobDetail.getJobDataMap().put("userName", user.getName());

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(new Date(timestamp))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private long getClosestNotificationTime(List<NotificationPeriod> notificationPeriods) {
        boolean closestDayFound = false;
        DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();
        List<Long> timestamps = new ArrayList<>();

        for (NotificationPeriod period : notificationPeriods) {
            if (period.getDayOfWeek().getValue() == currentDayOfWeek.getValue() &&
                    now().isBefore(period.getBeginPeriod())) {
                return LocalDateTime.now()
                        .withHour(period.getBeginPeriod().getHour())
                        .withMinute(period.getBeginPeriod().getMinute()).atZone(ZoneId.systemDefault())
                        .toInstant().toEpochMilli();
            }
        }

        for (int offset = 1; offset <= 6; offset++) {
            for (NotificationPeriod period : notificationPeriods) {
                if (period.getDayOfWeek().getValue() == currentDayOfWeek.plus(offset).getValue()) {
                    timestamps.add(LocalDateTime.now().plusDays(offset)
                            .withHour(period.getBeginPeriod().getHour())
                            .withMinute(period.getBeginPeriod().getMinute()).atZone(java.time.ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                    closestDayFound = true;
                }
            }

            if (closestDayFound) {
                break;
            }
        }
        return Collections.min(timestamps);
    }

    private boolean timeInRange(LocalTime now, NotificationPeriod period) {
        return now.isAfter(period.getBeginPeriod()) && now.isBefore(period.getEndPeriod());
    }
}