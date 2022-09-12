package xyz.hostileobject.resources;

import java.time.LocalTime;

public class TimeUtils {
    public static LocalTime toLocalTime(String time) {
        return LocalTime.of(Integer.parseInt(time.substring(0, time.indexOf(":"))),
                Integer.parseInt(time.substring(time.indexOf(":") + 1)));
    }

    public static LocalTime sumTime(String departureHour, String arrivalTime) {
        LocalTime arrTime = toLocalTime(departureHour);
        return arrTime.plusMinutes(Long.parseLong(arrivalTime));
    }

    public static boolean isAfterNow(String arrTime) {
        LocalTime currTime = LocalTime.of(6, 10);
        return currTime.isBefore(TimeUtils.toLocalTime(arrTime));
    }

    public static int compare(String time1, String time2) {
        return toLocalTime(time1).compareTo(toLocalTime(time2));
    }

}
