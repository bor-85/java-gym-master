package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        int expectedMondayCount = 1;
        int expectedTuesdayCount = 0;

        int resultMondayCount = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        int resultTuesdayCount = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(expectedMondayCount, resultMondayCount);

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(expectedTuesdayCount, resultTuesdayCount);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        List<TrainingSession> thursdayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        //подготовка к тесту понедельник
        int expectedMondayCount = 1;
        int resultMondayCount = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();

        //подготовка к тесту вторник
        int expectedTuesdayCount = 0;
        int resultTuesdayCount = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();

        //подготовка к тесту четверг
        int expectedThursdayCount = 2;
        int expectedThursdayHours1 = 13;
        int expectedThursdayHours2 = 20;

        int resultThursdayCount = thursdayTrainingSession.size();
        int resultThursdayHours1 = thursdayTrainingSession.getFirst().getTimeOfDay().getHours();
        int resultThursdayHours2 = thursdayTrainingSession.getLast().getTimeOfDay().getHours();

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(expectedMondayCount, resultMondayCount);
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(expectedThursdayCount, resultThursdayCount);
        Assertions.assertEquals(expectedThursdayHours1, resultThursdayHours1);
        Assertions.assertEquals(expectedThursdayHours2, resultThursdayHours2);
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(expectedTuesdayCount, resultTuesdayCount);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        int expectedMondayCount13 = 1;
        int expectedMondayCount14 = 0;

        int resultMondayCount13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size();
        int resultMondayCount14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size();
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(expectedMondayCount13, resultMondayCount13);
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(expectedMondayCount14, resultMondayCount14);
    }

    //Проверка работы метода, если 2 тренировки в одно время
    @Test
    void testGetTrainingSessionsForDayAndTimeSameTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Попенко", "Иван", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        int expectedMondayCount13 = 2;

        int resultMondayCount13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size();

        //Проверить, что за понедельник в 13:00 вернулось два занятия
        Assertions.assertEquals(expectedMondayCount13, resultMondayCount13);

    }

    //Проверка работы метода на пустом расписании
    @Test
    void testGetCountByCoachesIsEmptyTimetable() {
        Timetable timetable = new Timetable();

        int expectedCount = 0;

        int resultCount = timetable.getCountByCoaches().size();

        // Проверить, что метод отработал и вернул 0 элементов
        Assertions.assertEquals(expectedCount, resultCount);

    }

    //Проверка работы метода на одной записи в расписании
    @Test
    void testGetCountByCoachesSingle() {
        Timetable timetable = new Timetable();

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        int expectedCount = 1;

        int resultCount = timetable.getCountByCoaches().size();

        // Проверить, что метод отработал и вернул 1 элемент
        Assertions.assertEquals(expectedCount, resultCount);

    }

    //Проверка работы метода на нескольких записях в расписании и вывода тренеров в правильной последовательности
    @Test
    void testGetCountByCoachesMultiple() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Попенко", "Иван", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession trainingSession1 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession3 = new TrainingSession(groupAdult, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        Iterator<Coach> iterator = (Iterator<Coach>) timetable.getCountByCoaches().keySet().iterator();


        int expectedCount = 2;
        int expectedCountTrainings1 = 2;
        int expectedCountTrainings2 = 1;

        int resultCount = timetable.getCountByCoaches().size();
        Coach resultCoach1 = iterator.next();
        Coach resultCoach2 = iterator.next();
        int resultCountTrainings1 = timetable.getCountByCoaches().get(resultCoach1);
        int resultCountTrainings2 = timetable.getCountByCoaches().get(resultCoach2);

        // Проверить, что метод отработал и вернул 2 элемента
        Assertions.assertEquals(expectedCount, resultCount);

        // Проверить, что список тренеров в правильном порядке: сначала coach1 с 2-мя тренировками, потом coach2 с одной тренировкой
        //Сравнение объектов Coach
        Assertions.assertEquals(coach1, resultCoach1);
        Assertions.assertEquals(coach2, resultCoach2);

        //Сравнение количества тренировок
        Assertions.assertEquals(expectedCountTrainings1, resultCountTrainings1);
        Assertions.assertEquals(expectedCountTrainings2, resultCountTrainings2);

    }

}
