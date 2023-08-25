package ru.pgeenergo.notificationservice.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(schema = "notificationservice")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            schema = "notificationservice",
            name = "notification_period",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @OrderBy(value = "dayOfWeek,beginPeriod")
    private List<NotificationPeriod> notificationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NotificationPeriod> getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(List<NotificationPeriod> notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (getId() != user.getId()) {
            return false;
        }
        if (!getName().equals(user.getName())) {
            return false;
        }
        return getNotificationTime().equals(user.getNotificationTime());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getNotificationTime().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notificationTime=" + notificationTime +
                '}';
    }
}