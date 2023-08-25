package ru.pgeenergo.notificationservice.repository;

import org.springframework.stereotype.Repository;
import ru.pgeenergo.notificationservice.domain.User;

@Repository
public interface UserRepository extends DataRepository<User> {

}