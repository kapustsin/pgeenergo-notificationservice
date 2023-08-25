package ru.pgeenergo.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DataRepository<T> extends JpaRepository<T, Long> {

}