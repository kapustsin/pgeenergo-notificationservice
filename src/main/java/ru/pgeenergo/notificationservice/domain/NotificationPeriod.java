package ru.pgeenergo.notificationservice.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
public class NotificationPeriod {
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;
    @Column(columnDefinition = "TIME(0)")
    private LocalTime beginPeriod;
    @Column(columnDefinition = "TIME(0)")
    private LocalTime endPeriod;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(LocalTime beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public LocalTime getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalTime endPeriod) {
        this.endPeriod = endPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationPeriod that = (NotificationPeriod) o;

        if (getDayOfWeek() != that.getDayOfWeek()) {
            return false;
        }
        if (!getBeginPeriod().equals(that.getBeginPeriod())) {
            return false;
        }
        return getEndPeriod().equals(that.getEndPeriod());
    }

    @Override
    public int hashCode() {
        int result = getDayOfWeek().hashCode();
        result = 31 * result + getBeginPeriod().hashCode();
        result = 31 * result + getEndPeriod().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NotificationPeriod{" +
                "dayOfWeek=" + dayOfWeek +
                ", beginPeriod=" + beginPeriod +
                ", endPeriod=" + endPeriod +
                '}';
    }
}