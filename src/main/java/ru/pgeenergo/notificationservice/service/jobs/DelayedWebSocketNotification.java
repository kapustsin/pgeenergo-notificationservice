package ru.pgeenergo.notificationservice.service.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.pgeenergo.notificationservice.service.EventService;

public class DelayedWebSocketNotification implements Job {
    private final EventService eventService;

    public DelayedWebSocketNotification(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        String notification = (String) context.getJobDetail().getJobDataMap().get("notification");
        eventService.sendWebSocketNotification(notification);
    }
}