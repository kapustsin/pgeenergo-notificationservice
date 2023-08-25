package ru.pgeenergo.notificationservice.repository;

import org.springframework.stereotype.Repository;
import ru.pgeenergo.notificationservice.domain.Event;

@Repository
public interface EventRepository extends DataRepository<Event> {

}