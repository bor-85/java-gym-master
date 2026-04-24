package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    //отдельная коллекция под тренировки на каждый день недели, так как есть требование к сложности получения списка за день - O(1)
    private final Map<DayOfWeek, List<TrainingSession>> dayTrainingMap = new HashMap<>();
    //Коллекция для хранения Тренеров и количества их тренировок в неделю
    private final Map<Coach, Integer> coachCountTrainings = new HashMap<>();

    public Timetable() {
        // Инициализация всех дней недели пустыми TreeMap
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
        // Также можно инициализировать деньTrainingMap, если нужно
        for (DayOfWeek day : DayOfWeek.values()) {
            dayTrainingMap.put(day, new ArrayList<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        Coach coach = trainingSession.getCoach();

        //Добавление тренировки в расписание
        TreeMap<TimeOfDay, List<TrainingSession>> timeTrainings = timetable.get(dayOfWeek);
        timeTrainings.putIfAbsent(timeOfDay, new ArrayList<>());
        List<TrainingSession> listTrainings = timeTrainings.get(timeOfDay);
        listTrainings.add(trainingSession);

        //заполняем коллекцию - список тренировок на день недели
        List<TrainingSession> listTrainingsByDay = dayTrainingMap.get(dayOfWeek);
        listTrainingsByDay.add(trainingSession);
        Collections.sort(listTrainingsByDay);

        //заполняем коллекцию с тренером и его количеством тренировок
        coachCountTrainings.compute(coach, (k, v) -> (v == null) ? 1 : v + 1);

    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        return dayTrainingMap.getOrDefault(dayOfWeek, Collections.emptyList());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> timeTrainings = timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        return timeTrainings.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public Map<Coach, Integer> getCountByCoaches() {
        if (coachCountTrainings.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(coachCountTrainings.entrySet());
        list.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());
        Map<Coach, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
