package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek,TreeMap<TimeOfDay,List<TrainingSession>>> timetable = new HashMap<>();
    //отдельная коллекция под тренировки на каждый день недели
    private final Map<DayOfWeek,List<TrainingSession>> dayTrainingMap = new HashMap<>();
    //Коллекция для хранения Тренеров и количества их тренировок в неделю
    private final Map<Coach,Integer> coachCountTrainings = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        Coach coach = trainingSession.getCoach();

        TreeMap<TimeOfDay,List<TrainingSession>> timeTrainings = timetable.get(dayOfWeek);
        if (timeTrainings == null) {
            timeTrainings = new TreeMap<>();
            timetable.put(dayOfWeek, timeTrainings);
        }

        List<TrainingSession> listTrainings = timeTrainings.get(timeOfDay);
        if (listTrainings == null) {
            listTrainings = new ArrayList<>();
            timeTrainings.put(timeOfDay, listTrainings);
        }
        listTrainings.add(trainingSession);

        //заполняем коллекцию - список тренировок на день недели
        List<TrainingSession>  listTrainingsByDay = dayTrainingMap.get(dayOfWeek);
        if (listTrainingsByDay == null) {
            listTrainingsByDay = new ArrayList<>();
            dayTrainingMap.put(dayOfWeek, listTrainingsByDay);
        }
        listTrainingsByDay.add(trainingSession);
        Collections.sort(listTrainingsByDay);

        //заполняем коллекцию с тренером и его количеством тренировок
        if (coachCountTrainings.containsKey(coach)) {
            int countTraings = coachCountTrainings.get(coach);
            coachCountTrainings.put(coach, countTraings + 1);
        } else {
            coachCountTrainings.put(coach, 1);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (dayTrainingMap.get(dayOfWeek) == null) {
            return Collections.emptyList();
        }
        return dayTrainingMap.get(dayOfWeek);//Сложность O(1)
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> timeTrainings = timetable.get(dayOfWeek);
        if (timeTrainings == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> listTrainings = timeTrainings.get(timeOfDay);
        if (listTrainings == null) {
            return Collections.emptyList();
        }

        return listTrainings;
    }

    public Map<Coach,Integer> getCountByCoaches() {
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
