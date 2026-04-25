package ru.yandex.practicum.gym;

public class TrainingSession implements Comparable<TrainingSession> {

    //группа
    private final Group group;
    //тренер
    private final Coach coach;
    //день недели
    private final DayOfWeek dayOfWeek;
    //время начала занятия
    private final TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }


    @Override
    public int compareTo(TrainingSession o) {
        return this.timeOfDay.compareTo(o.timeOfDay);
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "group=" + group +
                ", coach=" + coach +
                ", dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                '}';
    }
}
