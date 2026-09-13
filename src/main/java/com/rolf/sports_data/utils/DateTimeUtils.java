package com.rolf.sports_data.utils;

import java.time.Duration;

public class DateTimeUtils {
    public static Long convertHoursToMilliseconds(String time) {
        if (time == null || time.isBlank()) {
            return null;
        }

        String normalizedTime = time.trim().replace("'", ":");

        String[] parts = normalizedTime.split(":");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Invalid time format. Expected HH:mm:ss.SSS or HH:mm:ss.SSS"
            );
        }

        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);

        String[] secondsParts = parts[2].split("\\.");

        long seconds = Long.parseLong(secondsParts[0]);

        long milliseconds = 0;

        if (secondsParts.length > 1) {
            String millis = secondsParts[1];

            // 1:23:06.8 -> 800 ms
            // 1:23:06.80 -> 800 ms
            // 1:23:06.801 -> 801 ms
            millis = (millis + "000").substring(0, 3);

            milliseconds = Long.parseLong(millis);
        }

        return Duration.ofHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds)
                .plusMillis(milliseconds)
                .toMillis();
    }
}
