package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    //Коллекция для хранения Тренеров и количества их тренировок в неделю
    private final Map<Coach, Integer> coachCountTrainings = new HashMap<>();

    public Timetable() {
        // Инициализация всех дней недели пустыми TreeMap
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
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

        //заполняем коллекцию с тренером и его количеством тренировок
        coachCountTrainings.compute(coach, (k, v) -> (v == null) ? 1 : v + 1);

    }

    public Collection<List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).values();

    }

    //добавил отдельный метод на подсчет количества сессий для прохождения тестов
    public int getCountTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        Collection<List<TrainingSession>> trainingsCollection = getTrainingSessionsForDay(dayOfWeek);
        int count = 0;
        for (List<TrainingSession> trainingList : trainingsCollection) {
            count += trainingList.size();
        }
        return count;

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
