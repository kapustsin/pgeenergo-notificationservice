package ru.pgeenergo.notificationservice.service.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import ru.pgeenergo.notificationservice.service.EventService;

import java.time.format.DateTimeFormatter;

@Component
public class DelayedNotification implements Job {
    private final EventService eventService;

    public DelayedNotification(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        String notification = (String) context.getJobDetail().getJobDataMap().get("notification");
        eventService.sendNotification(notification);
    }
}